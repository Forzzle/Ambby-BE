package com.forzzle.hodeum.gmap.payload.dto;

import java.util.List;

public record GooglePlacePreview(
    List<Place> places
) {

    public record Place(
        String id,
        Location location,
        DisplayName displayName,
        DisplayName primaryTypeDisplayName,
        String priceLevel // null 가능
    ) {

        public record Location(
            double latitude,
            double longitude
        ) {

        }

        public record DisplayName(
            String text,
            String languageCode
        ) {

        }
    }
}
