package ru.avtodoria;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.avtodoria.transformers.MyResponseTransformers;

import javax.annotation.PostConstruct;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${geo.auth.tokenName}")
    private String authHeaderName;

    @Value("${geo.auth.tokenValue}")
    private String authHeaderValue;

    private WireMockServer wireMockServer;

    @PostConstruct
    public void init() {
        wireMockServer =
                new WireMockServer(wireMockConfig().port(8888)
                        .extensions(MyResponseTransformers.class));
        wireMockServer.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void run(String... args) throws Exception {

        wireMockServer.stubFor(get(urlPathMatching("/geo/[0-9]+?"))
                .withHeader(authHeaderName, notMatching(authHeaderValue))
                .willReturn(forbidden()
                        .withBody("Api-key does not match")));

        wireMockServer.stubFor(get(urlMatching("/geo/[0-9]+?"))
                .withHeader(authHeaderName, equalTo(authHeaderValue))
                .willReturn(ok()
                        .withTransformers("my-response-transformer")));
    }
}
