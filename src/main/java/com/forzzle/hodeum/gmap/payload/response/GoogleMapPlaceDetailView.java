package com.forzzle.hodeum.gmap.payload.response;

import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.AccessibilityOptions;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.DisplayName;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.Location;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.PrimaryTypeDisplayName;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.RegularOpeningHours;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.Review;
import java.util.List;

public record GoogleMapPlaceDetailView(
    String id,
    String nationalPhoneNumber,
    String formattedAddress,
    double rating,
    RegularOpeningHours regularOpeningHours,
    int userRatingCount,
    DisplayName displayName,
    PrimaryTypeDisplayName primaryTypeDisplayName,
    List<Review> reviews,
    boolean allowsDogs,
    AccessibilityOptions accessibilityOptions,
    Location location
) {

    public static GoogleMapPlaceDetailView of(GoogleMapPlaceDetail google) {
        return new GoogleMapPlaceDetailView(
            google.id(),
            google.nationalPhoneNumber(),
            google.formattedAddress(),
            google.rating(),
            google.regularOpeningHours(),
            google.userRatingCount(),
            google.displayName(),
            google.primaryTypeDisplayName(),
            google.reviews(),
            google.allowsDogs(),
            google.accessibilityOptions(),
            new Location(google.location().latitude(), google.location().longitude())
        );
    }

}
