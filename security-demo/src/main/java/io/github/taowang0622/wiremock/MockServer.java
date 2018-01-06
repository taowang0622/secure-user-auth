package io.github.taowang0622.wiremock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

//This class actually is a client for connecting to the standalone WireMock server
public class MockServer {
    public static void main(String[] args) throws IOException {
        //The code below is to configure the wiremock server running locally!!!
        configureFor(8062);
        removeAllMappings(); //Equivalent to clean task in Maven life cycle!!

        mock("/order/1", "01");
        mock("/order/2", "02");
    }

    private static void mock(String url, String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/"+file+".json");
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray());
        stubFor(get(urlPathEqualTo(url)).willReturn(aResponse().withBody(content).withStatus(200)));
    }
}
