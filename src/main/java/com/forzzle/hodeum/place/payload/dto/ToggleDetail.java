package com.forzzle.hodeum.place.payload.dto;

import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail;
import com.forzzle.hodeum.tour.payload.TourPlaceDetail;
import com.forzzle.hodeum.tour.payload.TourPlaceDetail.Response.Body.Items.Item;

public record ToggleDetail(
    Common common,
    Blind blind,
    PhysicallyDisabled physicallyDisabled,
    Deaf deaf
) {

    public record Common(
        String parking,
        String route,
        String stroller,
        String lactationroom,
        String babysparechair,
        String infantsfamilyetc
    ) {

    }

    public record Blind(
        String publictransport,
        String elevator,
        String restroom,
        String braileblock,
        String helpdog,
        String guidehuman,
        String audioguide,
        String bigprint,
        String brailepromotion,
        String guidesystem,
        String blindhandicapetc
    ) {

    }

    public record PhysicallyDisabled(
        String publictransport,
        String exit,
        String elevator,
        String restroom,
        String handicapetc,
        String tourWheelchair,
        boolean wheelchairAccessibleParking,
        boolean wheelchairAccessibleEntrance,
        boolean wheelchairAccessibleSeating,
        boolean wheelchairAccessibleRestroom
    ) {

    }

    public record Deaf(
        String signguide,
        String videoguide,
        String hearinghandicapetc
    ) {

    }

    public static ToggleDetail of(GoogleMapPlaceDetail google, TourPlaceDetail tourPlaceDetail) {
        Item tour = tourPlaceDetail.response().body().items().item().get(0);

        return new ToggleDetail(
            new Common(
                tour.parking(),
                tour.route(),
                tour.stroller(),
                tour.lactationroom(),
                tour.babysparechair(),
                tour.infantsfamilyetc()
            ),
            new Blind(
                tour.publictransport(),
                tour.elevator(),
                tour.restroom(),
                tour.braileblock(),
                tour.helpdog(),
                tour.guidehuman(),
                tour.audioguide(),
                tour.bigprint(),
                tour.brailepromotion(),
                tour.guidesystem(),
                tour.blindhandicapetc()
            ),
            new PhysicallyDisabled(
                tour.publictransport(),
                tour.wheelchair(),
                tour.exit(),
                tour.elevator(),
                tour.restroom(),
                tour.handicapetc(),
                google.accessibilityOptions().wheelchairAccessibleParking(),
                google.accessibilityOptions().wheelchairAccessibleEntrance(),
                google.accessibilityOptions().wheelchairAccessibleSeating(),
                google.accessibilityOptions().wheelchairAccessibleRestroom()
            ),
            new Deaf(
                tour.signguide(),
                tour.videoguide(),
                tour.hearinghandicapetc()
            )
        );
    }
}
