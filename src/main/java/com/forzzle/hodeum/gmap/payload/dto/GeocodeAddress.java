package com.forzzle.hodeum.gmap.payload.dto;

import java.util.List;

public record GeocodeAddress(
    List<Result> results
) {

    public record Result(
        String formatted_address
    ) {

    }

}
