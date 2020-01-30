package ru.avtodoria;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.avtodoria.TestUtils;
import ru.avtodoria.config.TestConfiguration;
import ru.avtodoria.dao.GeoLocationDao;
import ru.avtodoria.model.GeoLocation;
import ru.avtodoria.service.GeoObjectService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeoLocationControllerTest {

    private static final String BASE_URL = "/geo/";
    private final Long id = 1L;

    private ParameterizedTypeReference<List<GeoLocation>> response = new ParameterizedTypeReference<List<GeoLocation>>() {
    };

    @MockBean
    GeoObjectService geoObjectService;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private GeoLocationDao geoLocationDao;

    private final HttpHeaders headers = new HttpHeaders();

    @Before
    public void init() {
        headers.add("AUTH_API_KEY", "dssadfsadfwe34234zxc");

        when(geoObjectService.getCoordinateObject(id))
                .thenReturn(Optional.ofNullable(TestUtils.getGeoObject(id)));
    }

    @Test
    @Sql(scripts = {"/sql/createGeoLocation.sql", "/sql/testFill.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getSuitableLocationsTest() {
        double r = 144.44;
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<GeoLocation>> exchange =
                restTemplate
                        .exchange(BASE_URL + "get/suitable/locations?id=" + id + "&r=" + r, HttpMethod.GET, new HttpEntity<>(headers), response);

        Assert.assertEquals(exchange.getStatusCode(), HttpStatus.OK);
        List<GeoLocation> gl = exchange.getBody();
        Assert.assertEquals(1, gl.size());
    }

    @Test
    public void getSuitableLocationsTestShoudReturn403() {
        double r = 144.44;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> exchange =
                restTemplate
                        .exchange(BASE_URL + "get/suitable/locations?id=" + id + "&r=" + r, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

        Assert.assertEquals(exchange.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    @Sql(scripts = {"/sql/createGeoLocation.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void fillTest() {
        int num = 3;
        ResponseEntity<Void> exchange = restTemplate
                .exchange(BASE_URL + "fill?n=" + num, HttpMethod.POST, new HttpEntity<>(headers), Void.class);

        Assert.assertEquals(exchange.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(num, geoLocationDao.getAllGeoLocations().get().size());

    }

    @Test
    public void fillTestShouldReturn403() {
        int num = 3;
        ResponseEntity<Void> exchange = restTemplate
                .exchange(BASE_URL + "fill?n=" + num, HttpMethod.POST, null, Void.class);

        Assert.assertEquals(exchange.getStatusCode(), HttpStatus.FORBIDDEN);
    }
}
