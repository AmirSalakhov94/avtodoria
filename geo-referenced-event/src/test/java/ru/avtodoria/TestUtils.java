package ru.avtodoria;

import ru.avtodoria.model.GeoLocation;
import ru.avtodoria.model.GeoObject;

import java.time.LocalDateTime;

public class TestUtils {

    public static GeoObject getGeoObject(Long id) {
        return new GeoObject()
                .setId(id)
                .setLat(43.12312)
                .setLgt(12.23423);
    }

    public static GeoLocation getGeoLocation(Long id) {
        return new GeoLocation()
                .setId(id)
                .setLgt(65.45345)
                .setLat(-13.423423)
                .setTime(LocalDateTime.now());
    }
}
