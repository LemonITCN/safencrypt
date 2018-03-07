package cn.lemonit.safencrypt.springboot.listener;

import cn.lemonit.safencrypt.springboot.core.exception.DelegateInvalidException;
import cn.lemonit.safencrypt.springboot.core.Safencrypt;
import cn.lemonit.safencrypt.springboot.proxy.SafencryptClientProxyImpl;
import cn.lemonit.safencrypt.springboot.proxy.SafencryptUserProxyImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SafencryptInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Safencrypt.init(new SafencryptClientProxyImpl(), new SafencryptUserProxyImpl());
        } catch (DelegateInvalidException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
