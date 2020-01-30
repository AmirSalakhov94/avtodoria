package ru.avtodoria;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.avtodoria.config.TestConfiguration;
import ru.avtodoria.dao.GeoLocationDao;
import ru.avtodoria.model.GeoLocation;
import ru.avtodoria.service.DistanceCalculator;
import ru.avtodoria.service.GeoObjectService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceCalculatorTest {

    @Autowired
    DistanceCalculator distanceCalculator;
    @Autowired
    GeoLocationDao geoLocationDao;

    Long id = 1L;

    @MockBean
    GeoObjectService geoObjectService;

    @Before
    public void init() {
        when(geoObjectService.getCoordinateObject(anyLong()))
                .thenReturn(Optional.ofNullable(TestUtils.getGeoObject(id)));
    }

    @Test
    @Sql(scripts = {"/sql/createGeoLocation.sql", "/sql/testFill.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void sphericalCalculateShouldTrue() {
        Optional<List<GeoLocation>> op = distanceCalculator.getSuitableLocations(id, 15.0);

        List<GeoLocation> l = op.get();
        assertEquals(1, l.size());
    }
}
