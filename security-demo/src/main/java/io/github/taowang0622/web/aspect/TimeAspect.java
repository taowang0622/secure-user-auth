package io.github.taowang0622.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
//@Aspect
public class TimeAspect {

    @Around("execution(* io.github.taowang0622.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Entering time aspect");

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("Arg is " + arg);
        }

        long startTime = new Date().getTime();

        Object retVal = proceedingJoinPoint.proceed();

        System.out.println("The execution of the method took " + (new Date().getTime() - startTime) + "ms");

        System.out.println("Existing from time aspect");

        //Passing the return value from matched method to the next stage
        // [method->aspect->ControllerAdvice->Interceptors->filter]
        return retVal;
    }
}
