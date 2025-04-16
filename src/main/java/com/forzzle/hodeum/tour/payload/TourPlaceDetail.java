package com.forzzle.hodeum.tour.payload;

import java.util.List;

public record TourPlaceDetail(Response response) {

    public record Response(Body body) {

        public record Body(Items items, int numOfRows, int pageNo, int totalCount) {

            public record Items(List<Item> item) {

                public record Item(
                    String contentid,
                    String parking,
                    String route,
                    String publictransport,
                    String ticketoffice,
                    String promotion,
                    String wheelchair,
                    String exit,
                    String elevator,
                    String restroom,
                    String auditorium,
                    String room,
                    String handicapetc,
                    String braileblock,
                    String helpdog,
                    String guidehuman,
                    String audioguide,
                    String bigprint,
                    String brailepromotion,
                    String guidesystem,
                    String blindhandicapetc,
                    String signguide,
                    String videoguide,
                    String hearingroom,
                    String hearinghandicapetc,
                    String stroller,
                    String lactationroom,
                    String babysparechair,
                    String infantsfamilyetc
                ) {

                }
            }
        }
    }

}
