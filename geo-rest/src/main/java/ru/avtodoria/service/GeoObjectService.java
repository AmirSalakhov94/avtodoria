package ru.avtodoria.service;

import ru.avtodoria.model.GeoObject;

import java.util.Optional;

public interface GeoObjectService {

    Optional<GeoObject> getCoordinateObject(Long id);
}
