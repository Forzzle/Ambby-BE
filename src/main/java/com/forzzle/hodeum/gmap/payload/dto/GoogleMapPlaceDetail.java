package com.forzzle.hodeum.gmap.payload.dto;

import java.util.List;

public record GoogleMapPlaceDetail(
    String id,
    String nationalPhoneNumber,
    String formattedAddress,
    double rating,
    RegularOpeningHours regularOpeningHours,
    int userRatingCount,
    DisplayName displayName,
    PrimaryTypeDisplayName primaryTypeDisplayName,
    List<Review> reviews,
    List<Photo> photos,
    boolean allowsDogs,
    AccessibilityOptions accessibilityOptions,
    Location location
) {

    public record RegularOpeningHours(
        boolean openNow,
        List<String> weekdayDescriptions
    ) {

    }

    public record DisplayName(
        String text
    ) {

    }

    public record PrimaryTypeDisplayName(
        String text
    ) {

    }

    public record Review(
        ReviewText text
    ) {

        public record ReviewText(
            String text,
            String languageCode
        ) {

        }
    }

    public record Photo(
        List<AuthorAttribution> authorAttributions,
        String googleMapsUri
    ) {

        public record AuthorAttribution(
            String photoUri
        ) {

        }
    }

    public record AccessibilityOptions(
        boolean wheelchairAccessibleParking,
        boolean wheelchairAccessibleEntrance,
        boolean wheelchairAccessibleSeating,
        boolean wheelchairAccessibleRestroom
    ) {

    }

    public record Location(
        double latitude,
        double longitude
    ) {

    }

}
