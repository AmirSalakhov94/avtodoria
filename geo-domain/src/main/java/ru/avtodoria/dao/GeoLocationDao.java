package ru.avtodoria.dao;

import ru.avtodoria.model.GeoLocation;

import java.util.List;
import java.util.Optional;

public interface GeoLocationDao {

    Optional<GeoLocation> getGeoLocationById(Long id);

    Optional<List<GeoLocation>> getAllGeoLocations();

    boolean createGeoLocation(GeoLocation geoLocation);
}
