package ru.avtodoria.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.avtodoria.model.GeoLocation;
import ru.avtodoria.service.FillGeoLocationService;
import ru.avtodoria.service.DistanceCalculator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GeoLocationController implements GeoLocationApi {

    private final DistanceCalculator sphericalDistanceCalculator;
    private final FillGeoLocationService fillGeoLocationService;

    @Override
    public ResponseEntity<List<GeoLocation>> getSuitableLocations(@RequestParam("id") Long id,
                                                                  @RequestParam("r") Double r) {
        return
                sphericalDistanceCalculator.getSuitableLocations(id, r)
                        .map(l -> ResponseEntity.ok()
                                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .body(l))
                        .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Override
    public void fillData(@RequestParam("n") Integer num) {
        log.debug("Fill with random values, quantity: {}", num);
        fillGeoLocationService.fillRandomGeoLocation(num);
    }
}
