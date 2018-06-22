package cn.lemonit.safencrypt.springboot.core.service;

import cn.lemonit.safencrypt.springboot.core.Safencrypt;
import cn.lemonit.safencrypt.springboot.core.util.HMAC;

import javax.servlet.http.HttpServletRequest;

/**
 * 签名业务
 */
public class SafencryptSignService {

    public static boolean checkSign(HttpServletRequest request) {
//        String url = request.getRequestURI() + "?" + request.getQueryString();
//        if (url.contains("safencrypt/apply-public-key") || url.contains("safencrypt/sign-up-client"))
//            return true;
//        url = url.substring(0, url.indexOf("sign=") - 1);
//
//        System.out.println(url);
//        String identifier = Safencrypt.getClientProxy().getClientIdentifierWithCToken(request.getParameter("flag"));
//        String localSign = HMAC.hmacMD5(url, identifier);
//
//        return localSign.equals(request.getParameter("sign"));
        return true;
    }

}
