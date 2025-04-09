package com.forzzle.hodeum.gmap.payload.dto;

import java.util.List;

public record PlaceDetail(
    String id,
    String nationalPhoneNumber,
    String formattedAddress,
    double rating,
    RegularOpeningHours regularOpeningHours,
    int userRatingCount,
    DisplayName displayName,
    PrimaryTypeDisplayName primaryTypeDisplayName,
    boolean reservable,
    List<Review> reviews,
    boolean menuForChildren,
    boolean allowsDogs,
    boolean restroom,
    PaymentOptions paymentOptions,
    AccessibilityOptions accessibilityOptions
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

    public record PaymentOptions(
        boolean acceptsCreditCards,
        boolean acceptsCashOnly
    ) {

    }

    public record AccessibilityOptions(
        boolean wheelchairAccessibleParking,
        boolean wheelchairAccessibleEntrance,
        boolean wheelchairAccessibleSeating
    ) {

    }

}
