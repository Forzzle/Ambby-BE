package com.forzzle.hodeum.place.payload.response;

import com.forzzle.hodeum.gmap.payload.dto.PlaceDetail;

public record PlaceDetailResponse(
    PlaceDetail placeDetail,
    String reviewSummary
) {

}
