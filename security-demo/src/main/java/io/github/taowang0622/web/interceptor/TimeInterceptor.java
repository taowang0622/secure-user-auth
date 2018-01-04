package io.github.taowang0622.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

//@Component
public class TimeInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        System.out.println("Entering preHandler");

        System.out.println(((HandlerMethod)o).getBean().getClass().getName()); //Print out the name of the controller!!!
        System.out.println(((HandlerMethod)o).getMethod()); //Print out the name of the handler method!!

        //Transferring data between "preHandler", "postHandler" and "afterCompletion" is completed by "httpServletRequest" and "httpServletResponse"
        httpServletRequest.setAttribute("startTime", new Date().getTime());

        System.out.println("Exiting from preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("Entering postHandler");

        long startTime = (long)httpServletRequest.getAttribute("startTime");
        System.out.println(((HandlerMethod)o).getMethod() + " takes " + (new Date().getTime() - startTime) + "ms");

        System.out.println("Exiting from postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        System.out.println("Entering afterCompletion");

        long startTime = (long)httpServletRequest.getAttribute("startTime");
        System.out.println(((HandlerMethod)o).getMethod() + " takes " + (new Date().getTime() - startTime) + "ms");

        System.out.println("The thrown exception is " + e);
        System.out.println("Exiting from afterCompletion");
    }
}
