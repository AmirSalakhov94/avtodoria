package ru.avtodoria.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class GeoObject {
    private Long id;
    private Double lat;
    private Double lgt;
}
