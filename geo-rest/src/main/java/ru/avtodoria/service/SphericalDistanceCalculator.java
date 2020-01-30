package ru.avtodoria.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.avtodoria.dao.GeoLocationDao;
import ru.avtodoria.model.GeoLocation;
import ru.avtodoria.model.GeoObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SphericalDistanceCalculator implements DistanceCalculator {

    private static final Integer EARTH_RADIUS = 6372795;

    private final GeoObjectService geoObjectService;
    private final GeoLocationDao geoLocationDao;

    @Override
    public Optional<List<GeoLocation>> getSuitableLocations(Long id, Double r) {
        log.debug("Geo object id - {}, radius - {}", id, r);
        return geoObjectService.getCoordinateObject(id).flatMap(centralLocation -> geoLocationDao.getAllGeoLocations()
                .map(locations -> locations.stream()
                        .filter(location -> calculateDistance(centralLocation, location, r))
                        .collect(Collectors.toList())));
    }

    private boolean calculateDistance(GeoObject centralLocation, GeoLocation location, Double radiusFromCentral) {
        double radiansCentralLat = Math.toRadians(centralLocation.getLat());
        double radiansLat = Math.toRadians(location.getLat());
        double radiansCentralLgt = Math.toRadians(centralLocation.getLgt());
        double radiansLgt = Math.toRadians(location.getLgt());

        double cosCentralLat = Math.cos(radiansCentralLat);
        double cosLat = Math.cos(radiansLat);
        double sinCentralLat = Math.sin(radiansCentralLat);
        double sinLat = Math.sin(radiansLat);

        double delta = radiansLgt - radiansCentralLgt;
        double cosDelta = Math.cos(delta);
        double sinDelta = Math.sin(delta);

        double y = Math.sqrt(Math.pow(cosLat * sinDelta, 2) + Math.pow(cosCentralLat * sinLat - sinCentralLat * cosLat * cosDelta, 2));
        double x = sinCentralLat * sinLat + cosCentralLat + cosLat * cosDelta;

        double dist = Math.atan2(y, x) * EARTH_RADIUS;

        return radiusFromCentral >= dist;
    }
}
