package win.liuri.safencrypt.listener;

import win.liuri.safencrypt.core.Safencrypt;
import win.liuri.safencrypt.core.exception.DelegateInvalidException;
import win.liuri.safencrypt.proxy.SafencryptClientProxyImpl;
import win.liuri.safencrypt.proxy.SafencryptUserProxyImpl;

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
