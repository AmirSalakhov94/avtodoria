package ru.avtodoria.dao;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.avtodoria.mapper.GeoLocationMapper;
import ru.avtodoria.model.GeoLocation;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class GeoLocationDaoImpl implements GeoLocationDao {

    private final String SQL_FIND_GEO_LOCATION = "select * from geo_location where id = :id";
    private final String SQL_GET_ALL = "select * from geo_location";
    private final String SQL_INSERT_GEO_LOCATION = "insert into geo_location(lat, lgt, time) values(:lat, :lgt, :time)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<GeoLocation> getGeoLocationById(Long id) {
        return Try.of(() -> namedParameterJdbcTemplate
                .queryForObject(SQL_FIND_GEO_LOCATION, Collections.singletonMap("id", id), new GeoLocationMapper()))
                .onFailure(e -> log.error("Unable to find object, an error occurred", e))
                .onSuccess(s -> log.debug("Object found: {}", s))
                .toJavaOptional();
    }

    @Override
    public Optional<List<GeoLocation>> getAllGeoLocations() {
        return Try.of(() -> namedParameterJdbcTemplate.query(SQL_GET_ALL, new GeoLocationMapper()))
                .onFailure(e -> log.error("Unable to find all objects, an error occurred", e))
                .onSuccess(s -> log.debug("Objects found: {}", s))
                .toJavaOptional();
    }

    @Override
    public boolean createGeoLocation(GeoLocation geoLocation) {
        Map<String, Object> p = new HashMap<>();
        p.put("lat", geoLocation.getLat());
        p.put("lgt", geoLocation.getLgt());
        p.put("time", geoLocation.getTime());

        return Try.of(() -> namedParameterJdbcTemplate.update(SQL_INSERT_GEO_LOCATION, p))
                .onFailure(e -> log.error("Unable to save, an error occurred", e))
                .onSuccess(s -> log.debug("Save success: {}", s))
                .toJavaOptional()
                .filter(r -> r > 0)
                .isPresent();
    }
}
