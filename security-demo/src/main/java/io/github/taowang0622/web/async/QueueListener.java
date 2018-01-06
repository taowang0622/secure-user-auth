package io.github.taowang0622.web.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent>{
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //Since we need to execute an infinite loop, there's a need to put it into a thread, otherwise the main thread would be blocked!!
        new Thread(() -> {
            while (true) {
                if (StringUtils.isNotBlank(mockQueue.getProcessedOrder())) {
                    String processedOrder = mockQueue.getProcessedOrder();
                    logger.info("Return the result of processing the given order: {}", processedOrder);
                    deferredResultHolder.getMap().get(processedOrder).setResult("Order processing successful!!");

                    mockQueue.setProcessedOrder(null); //Equivalent to dequeue!!!!
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
