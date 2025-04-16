package com.forzzle.hodeum.place.payload.response;

import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail;
import com.forzzle.hodeum.tour.payload.TourPlaceDetail;

public record PlaceDetailResponse(
    GoogleMapPlaceDetail googleMapPlaceDetail,
    String reviewSummary,
    String[] soundList,
    TourPlaceDetail tourPlaceDetail
) {

}
