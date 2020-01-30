package ru.avtodoria.transformers;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import org.springframework.http.MediaType;

public class MyResponseTransformers extends ResponseDefinitionTransformer {

    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource, Parameters parameters) {

        double lat = (Math.random() * 180.0) - 90.0;
        double lgt = (Math.random() * 360.0) - 180.0;

        Long id = Long.parseLong(request.getUrl().split("/")[2]);
        return new ResponseDefinitionBuilder()
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)
                .withBody("{\n" +
                        "    \"id\": \"" + id + "\",\n" +
                        "    \"lat\": \"" + lat + "\",\n" +
                        "    \"lgt\": \"" + lgt + "\"\n" +
                        "}")
                .build();
    }

    public String getName() {
        return "my-response-transformer";
    }
}