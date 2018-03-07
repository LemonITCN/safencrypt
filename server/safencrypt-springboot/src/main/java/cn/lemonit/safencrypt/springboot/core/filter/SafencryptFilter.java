package cn.lemonit.safencrypt.springboot.core.filter;

import cn.lemonit.safencrypt.springboot.core.service.SafencryptSignService;
import cn.lemonit.safencrypt.springboot.core.wrapper.SafencryptRequestWrapper;
import cn.lemonit.safencrypt.springboot.core.wrapper.SafencryptResponseWrapper;
import cn.lemonit.safencrypt.springboot.core.service.SafencryptResponseService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SafencryptFilter implements Filter {

    private SafencryptResponseService responseService;
    private SafencryptSignService signService;

    public SafencryptResponseService getResponseService() {
        if (responseService == null)
            responseService = new SafencryptResponseService();
        return responseService;
    }

    public SafencryptSignService getSignService() {
        if (signService == null)
            signService = new SafencryptSignService();
        return signService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            // 需要验证签名操作
            boolean checkResult = getSignService().checkSign((HttpServletRequest) servletRequest);
            System.out.println("验证签名结果：" + checkResult);
            if (!checkResult) {
                throw new ServletException("验证签名失败");
            }
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        // 替换响应对象为包装过的响应对象
        SafencryptResponseWrapper responseWrapper = new SafencryptResponseWrapper(httpServletResponse);
        SafencryptRequestWrapper requestWrapper = new SafencryptRequestWrapper(httpServletRequest);
        if ("DELETEGET".contains(((HttpServletRequest) servletRequest).getMethod()))
            filterChain.doFilter(servletRequest, servletResponse);
        else {
            filterChain.doFilter(requestWrapper, responseWrapper);
            // 拿到正常的响应结果，并从请求中获取参数，准备加密响应
            String content = responseWrapper.getCaptureAsString();
            if (requestWrapper.getParameterMap().size() > 0) {
                Integer type = Integer.valueOf(requestWrapper.getParameter("type"));
                String flag = requestWrapper.getParameter("flag");
                String resp = getResponseService().encryptResponse(content, type, flag);
                System.out.println("resp = " + resp);

                // 拿到加密后的响应值，写出给客户端
                httpServletResponse.setContentLength(resp.getBytes().length);
                PrintWriter writer = httpServletResponse.getWriter();
                writer.write(resp);
                writer.flush();
                writer.close();
            }
        }
    }

    @Override
    public void destroy() {

    }

}
