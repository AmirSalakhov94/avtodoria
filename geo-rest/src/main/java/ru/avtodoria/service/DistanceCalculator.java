package ru.avtodoria.service;

import ru.avtodoria.model.GeoLocation;

import java.util.List;
import java.util.Optional;

public interface DistanceCalculator {

    Optional<List<GeoLocation>> getSuitableLocations(Long id, Double r);
}
