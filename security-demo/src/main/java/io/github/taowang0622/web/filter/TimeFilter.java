package io.github.taowang0622.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

//for filters from other library, we cannot add @Component to it, and Spring Boot has no web.xml,
// so we have to use JAVA-based configuration!!
//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Time Filter Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Time filter starts");
        long start = new Date().getTime();
        filterChain.doFilter(servletRequest, servletResponse);
        long end = new Date().getTime();
        System.out.println((end - start) + "ms elapsed");
        System.out.println("Time filter ends");
    }

    @Override
    public void destroy() {
        System.out.println("Time Filter Destruction");
    }
}
