package com.forzzle.hodeum.gmap.application;

import com.forzzle.hodeum.gmap.payload.dto.GeocodeAddress;
import com.forzzle.hodeum.gmap.payload.dto.GooglePlacePreview;
import com.forzzle.hodeum.gmap.payload.dto.GooglePlaceTextQuery;
import com.forzzle.hodeum.gmap.payload.dto.PlaceDetail;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleMapClient {

    private final WebClient placesClient;
    private final WebClient mapsClient;

    @Value("${google-map.api.key}")
    private String apiKey;

    public GoogleMapClient(WebClient.Builder builder) {
        this.placesClient = builder.baseUrl("https://places.googleapis.com")
            .build();
        this.mapsClient = builder.baseUrl("https://maps.googleapis.com")
            .build();
    }

    public GooglePlacePreview searchPlaces(String query) {
        GooglePlaceTextQuery request = new GooglePlaceTextQuery(query);

        GooglePlacePreview response = placesClient.post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("languageCode", "ko")
                .path("/v1/places:searchText")
                .build())
            .header("Content-Type", "application/json")
            .header("X-Goog-Api-Key", apiKey)
            .header("X-Goog-FieldMask",
                "places.id,places.displayName,places.priceLevel,places.primaryTypeDisplayName,places.location")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(GooglePlacePreview.class)
            .block();

        return response;
    }

    public String searchAddressWithCoordinate(double latitude, double longitude) {
        GeocodeAddress response = mapsClient.post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("language", "ko")
                .queryParam("key", apiKey)
                .queryParam("latlng", latitude + "," + longitude)
                .path("/maps/api/geocode/json")
                .build())
            .retrieve()
            .bodyToMono(GeocodeAddress.class)
            .block();

        String address = response.results().get(0).formatted_address();
        String[] adr = address.split(" ");
        String result = String.join(" ", Arrays.copyOfRange(adr, 1, adr.length));
        return result;
    }

    public PlaceDetail getPlaceDetail(String placeId) {
        String fields = "id,displayName,formattedAddress,regularOpeningHours,rating,userRatingCount,reviews.text,primaryTypeDisplayName,accessibilityOptions,paymentOptions,nationalPhoneNumber,restroom,editorialSummary,reservable,delivery,menuForChildren,allowsDogs";
        PlaceDetail response = placesClient.get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("languageCode", "ko")
                .queryParam("key", apiKey)
                .queryParam("fields", fields)
                .path("/v1/places/" + placeId)
                .build())
            .retrieve()
            .bodyToMono(PlaceDetail.class)
            .block();

        return response;
    }
}
