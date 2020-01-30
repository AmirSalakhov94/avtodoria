package ru.avtodoria.mapper;


import org.springframework.jdbc.core.RowMapper;
import ru.avtodoria.model.GeoLocation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeoLocationMapper implements RowMapper<GeoLocation> {

    @Override
    public GeoLocation mapRow(ResultSet resultSet, int i) throws SQLException {
        return new GeoLocation()
                .setId(resultSet.getLong("id"))
                .setLat(resultSet.getDouble("lat"))
                .setLgt(resultSet.getDouble("lgt"))
                .setTime(resultSet.getTimestamp("time").toLocalDateTime());
    }
}
