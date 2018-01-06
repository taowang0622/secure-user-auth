package io.github.taowang0622.web.async;

import ch.qos.logback.classic.Level;
import ch.qos.logback.core.util.StatusPrinter;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(getClass());


//    @GetMapping("/order")
//    public Callable<String> order() throws InterruptedException {
//        logger.info("Main thread starts");
//
//        Callable<String> result = () -> {
//            logger.info("Child thread starts");
//            Thread.sleep(1000);
//            logger.info("Child thread ends");
//            return "success";
//        };
//
//        logger.info("Main thread ends");
//        return result;
//    }

    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @GetMapping("/order")
    public DeferredResult<String> order() throws InterruptedException {
        logger.info("Main thread starts");

        String orderNum = RandomStringUtils.randomNumeric(8);
        mockQueue.setOrder(orderNum);  //Equivalent to enqueue!!!

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNum, result);

        logger.info("Main thread ends");
        return result;
    }

    public static void main(String[] args) {
        //Using Logger in logback-classic instead of in slf4j, so we can set its logging level!!
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("io.github.taowang0622.web");



        logger.setLevel(Level.INFO); //only messages above the INFO level can be logged!!!
        logger.warn("WARN > INFO in logging level"); //This message will be logged!!
        logger.debug("DEBUG < INFO in logging level"); //This message won't be logged!!

        Logger childLogger = LoggerFactory.getLogger("io.github.taowang0622.web.async.AsyncController");
        childLogger.info("....");  //childLogger inherits the logging level INFO from its ancestor "logger", so this message will be logged
        childLogger.debug("...");  //won't be logged!!
    }
}
