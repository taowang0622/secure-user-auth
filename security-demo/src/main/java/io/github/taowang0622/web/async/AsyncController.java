package io.github.taowang0622.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/order")
    public Callable<String> order() throws InterruptedException {
        logger.info("Main thread starts");

        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("Child thread starts");
                Thread.sleep(1000);
                logger.info("Child thread ends");
                return "success";
            }
        };

        logger.info("Main thread ends");
        return result;
    }
}
