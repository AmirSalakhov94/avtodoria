package ru.avtodoria.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avtodoria.model.GeoLocation;

import java.util.List;

@RequestMapping("/geo")
public interface GeoLocationApi {

    @GetMapping("/get/suitable/locations")
    ResponseEntity<List<GeoLocation>> getSuitableLocations(@RequestParam(name = "id") Long id,
                                        @RequestParam(name = "r") Double r);

    @PostMapping("/fill")
    void fillData(@RequestParam(name = "n") Integer num);
}
