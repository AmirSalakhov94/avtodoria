package ru.avtodoria.service;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.avtodoria.model.GeoObject;

import java.util.Optional;

@Slf4j
@Service
public class GeoObjectServiceImpl implements GeoObjectService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String endpoint = "/geo/";

    @Value("${geo.object.url}")
    private String baseUrl;

    @Value("${geo.auth.tokenName}")
    private String authHeaderName;

    @Value("${geo.auth.tokenValue}")
    private String authHeaderValue;

    @Override
    public Optional<GeoObject> getCoordinateObject(Long id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        httpHeaders.set(authHeaderName, authHeaderValue);

        return Try.of(() -> restTemplate.exchange(
                baseUrl + endpoint + id, HttpMethod.GET, new HttpEntity<>(httpHeaders), GeoObject.class))
                .onFailure(e -> log.error("Сould not get an answer: ", e))
                .onSuccess(s -> log.debug("Answer received: {}", s))
                .map(ResponseEntity::getBody)
                .toJavaOptional();
    }
}
