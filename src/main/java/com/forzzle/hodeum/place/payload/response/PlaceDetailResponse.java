package com.forzzle.hodeum.place.payload.response;

import com.forzzle.hodeum.gmap.payload.response.GoogleMapPlaceDetailView;
import com.forzzle.hodeum.place.payload.dto.ToggleDetail;
import java.util.List;

public record PlaceDetailResponse(
    GoogleMapPlaceDetailView info,
    String reviewSummary,
    String[] soundList,
    ToggleDetail toggle,
    List<String> photos
) {

}
