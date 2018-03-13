package cn.lemonit.safencrypt.springboot.core.wrapper;

import cn.lemonit.safencrypt.springboot.core.bean.ClientInfo;
import cn.lemonit.safencrypt.springboot.core.exception.EncryptRequestInvalidException;
import cn.lemonit.safencrypt.springboot.core.service.RSAEncryptService;
import cn.lemonit.safencrypt.springboot.core.util.AES;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import cn.lemonit.safencrypt.springboot.core.Safencrypt;
import cn.lemonit.safencrypt.springboot.core.exception.FlagInvalidException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SafencryptRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> parameterMap = new HashMap<>();

    public SafencryptRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            if (request.getParameterMap().size() > 0 && request.getParameter("type") != null && request.getParameterMap().get("type").length > 0)
                parameterMap = decryptParameterMap(request.getParameterMap());
            if ("DELETEGET".indexOf(request.getMethod()) >= 0)
                parameterMap = request.getParameterMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return decryptInputStream(super.getInputStream());
    }

    @Override
    public String getParameter(String name) {
        String[] items = parameterMap.get(name);
        if (items == null || items.length <= 0)
            return null;
        else
            return items[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<>(parameterMap.keySet());
        return vector.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }

    private ServletInputStream decryptInputStream(ServletInputStream servletInputStream) {
        String content;
        try {
            content = IOUtils.toString(servletInputStream, "UTF-8");
            Gson gson = new Gson();
            Map<String, String> map = gson.fromJson(content, new TypeToken<HashMap<String, String>>() {
            }.getType());
            Integer type = Integer.valueOf(map.get("type"));
            String flag = flag = map.get("flag");
            String data = data = map.get("data");
            String result = "";
            parameterMap.put("type", new String[]{type + ""});
            parameterMap.put("flag", new String[]{flag});
            if (data.indexOf("%") > 0)
                data = URLDecoder.decode(data, "UTF-8");
            if (type == 2) {
                // 注册客户端的加密
                ClientInfo clientInfo = new ClientInfo(RSAEncryptService.decryptJSRequest(flag, data).substring(0, 16));
                result = gson.toJson(clientInfo);
            }
            if (type == 3) {
                // 基于客户端信息的加密
                result = AES.decrypt(data, Safencrypt.getClientProxy().getClientIdentifierWithCToken(flag));
            }
            if (type == 4) {
                // 基于用户信息的加密
                result = AES.decrypt(data, Safencrypt.getUserProxy().getUTokenWithCToken(flag));
            }
            System.out.println(result);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getBytes());
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return inputStream.read();
                }
            };


        } catch (Exception e) {
            e.printStackTrace();
        }
        return servletInputStream;
    }

    /**
     * 解密请求参数map
     *
     * @param map 请求参数map
     * @return 解密后的map
     */
    private Map<String, String[]> decryptParameterMap(Map<String, String[]> map) throws EncryptRequestInvalidException, FlagInvalidException {
        Map<String, String[]> resultMap = new HashMap<>();
        if (!map.containsKey("type"))
            throw new EncryptRequestInvalidException();
        Integer type = Integer.valueOf(map.get("type")[0]);
        String flag = null;
        String data = null;
        if (type >= 2) {
            flag = map.get("flag")[0];
            data = map.get("data")[0];
        }
        if (type == 1) {
            return map;
        } else if (type == 2) {
            resultMap.put("identifier", new String[]{RSAEncryptService.decryptJSRequest(flag, data)});
        } else if (type == 3) {
            String identifier = Safencrypt.getClientProxy().getClientIdentifierWithCToken(flag);
            String content = null;
            try {
                System.out.println("pre data = " + data);
                content = AES.decrypt(data, identifier);
                content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
                for (String item : content.split("&")) {
                    Integer loc = item.indexOf("=");
                    resultMap.put(item.substring(0, loc), new String[]{item.substring(loc + 1)});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String content = null;
            try {
                content = AES.decrypt(data, Safencrypt.getUserProxy().getUTokenWithCToken(flag));
                content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
                for (String item : content.split("&")) {
                    Integer loc = item.indexOf("=");
                    resultMap.put(item.substring(0, loc), new String[]{item.substring(loc + 1)});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

}