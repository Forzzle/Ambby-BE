package com.forzzle.hodeum.tour.application;

import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.Location;
import com.forzzle.hodeum.tour.payload.TourPlaceDetail;
import com.forzzle.hodeum.tour.payload.TourPlacePreiew;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TourClient {

    private final WebClient webClient;

    @Value("${tour.api.key}")
    private String apiKey;

    private String encodedKey;

    public TourClient(WebClient.Builder builder) {
        this.webClient = builder
            .baseUrl("https://apis.data.go.kr")
            .build();
    }

    @PostConstruct
    public void init() {
        this.encodedKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
    }

    public TourPlacePreiew getPlaceIdWithLocation(Location location) {
        String uriString = "https://apis.data.go.kr/B551011/KorWithService1/locationBasedList1?"
                + "numOfRows=1"
                + "&pageNo=0"
                + "&MobileOS=WIN"
                + "&MobileApp=HODEUM"
                + "&mapX=" + location.longitude()
                + "&mapY=" + location.latitude()
                + "&radius=350"
                + "&_type=JSON"
                + "&serviceKey=" + apiKey;
        URI uri = URI.create(uriString);

        TourPlacePreiew response = webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(TourPlacePreiew.class)
            .block();
        return response;
    }

    public TourPlaceDetail getPlaceDetail(String contentId) {
        String uriString = "https://apis.data.go.kr/B551011/KorWithService1/detailWithTour1?"
            + "numOfRows=1"
            + "&pageNo=0"
            + "&MobileOS=WIN"
            + "&MobileApp=HODEUM"
            + "&contentId=" + contentId
            + "&_type=JSON"
            + "&serviceKey=" + apiKey;
        URI uri = URI.create(uriString);

        TourPlaceDetail response = webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(TourPlaceDetail.class)
            .block();
        return response;
    }
}
