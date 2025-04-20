package com.forzzle.hodeum.place.payload.response;

import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail;
import com.forzzle.hodeum.place.payload.dto.ToggleDetail;

public record PlaceDetailResponse(
    GoogleMapPlaceDetail info,
    String reviewSummary,
    String[] soundList,
    ToggleDetail toggle
) {

}
