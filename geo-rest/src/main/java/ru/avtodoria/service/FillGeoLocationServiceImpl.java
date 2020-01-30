package ru.avtodoria.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.avtodoria.dao.GeoLocationDao;
import ru.avtodoria.model.GeoLocation;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class FillGeoLocationServiceImpl implements FillGeoLocationService {

    private final GeoLocationDao geoLocationDao;

    @Override
    public void fillRandomGeoLocation(Integer num) {
        log.debug("Number random object create - {}", num);
        for (int i = 0; i < num; i++) {
            double latitude = (Math.random() * 180.0) - 90.0;
            double longitude = (Math.random() * 360.0) - 180.0;
            geoLocationDao.createGeoLocation(new GeoLocation()
                    .setLat(latitude)
                    .setLgt(longitude)
                    .setTime(LocalDateTime.now()));
        }
    }
}
