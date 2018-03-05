package win.liuri.safencrypt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import win.liuri.safencrypt.core.filter.SafencryptFilter;
import win.liuri.safencrypt.listener.SafencryptInit;

@Configuration
public class SafencryptConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加Safencrypt过滤
     *
     * @return 过滤注册对象
     */
    @Bean
    public FilterRegistrationBean addFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new SafencryptFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<SafencryptInit> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<SafencryptInit> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new SafencryptInit());
        return servletListenerRegistrationBean;
    }

}
