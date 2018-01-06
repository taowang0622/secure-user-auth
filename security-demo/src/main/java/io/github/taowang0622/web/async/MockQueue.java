package io.github.taowang0622.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String order;
    private String processedOrder;

    public String getOrder() {
        return order;
    }

    //Equivalent to enqueue!!
    public void setOrder(String order) throws InterruptedException {
        //Put the logic into a thread to simulate the case of another server/application processing the passed order!!
        new Thread(() -> {
            logger.info("The order accepted: {}", order);
            try {
                Thread.sleep(1000); //Mocking the process of processing the passed order
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.processedOrder = order;
            logger.info("The order processed: {}", this.processedOrder);
        }).start();
    }

    public String getProcessedOrder() {
        return processedOrder;
    }

    public void setProcessedOrder(String processedOrder) {
        this.processedOrder = processedOrder;
    }
}
