package com.forzzle.hodeum.gmap.application;

import com.forzzle.hodeum.gmap.payload.dto.GeocodeAddress;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.Photo;
import com.forzzle.hodeum.gmap.payload.dto.GooglePlacePreview;
import com.forzzle.hodeum.gmap.payload.dto.GooglePlaceTextQuery;
import com.forzzle.hodeum.place.payload.dto.PhotoURI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public GooglePlacePreview searchPlaces(String query, String pageToken) {
        GooglePlaceTextQuery request = new GooglePlaceTextQuery(query);

        GooglePlacePreview response = placesClient.post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("languageCode", "ko")
                .queryParam("pageSize", 5)
                .queryParam("pageToken", pageToken)
                .path("/v1/places:searchText")
                .build())
            .header("Content-Type", "application/json")
            .header("X-Goog-Api-Key", apiKey)
            .header("X-Goog-FieldMask",
                "places.id,places.displayName,places.priceLevel,places.primaryTypeDisplayName,places.location,nextPageToken")
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

    public GoogleMapPlaceDetail getPlaceDetail(String placeId) {
        String fields = "id,displayName,formattedAddress,regularOpeningHours,rating,userRatingCount,reviews.text,primaryTypeDisplayName,accessibilityOptions,nationalPhoneNumber,allowsDogs,location,photos";
        GoogleMapPlaceDetail response = placesClient.get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("languageCode", "ko")
                .queryParam("key", apiKey)
                .queryParam("fields", fields)
                .path("/v1/places/" + placeId)
                .build())
            .retrieve()
            .bodyToMono(GoogleMapPlaceDetail.class)
            .block();

        return response;
    }

    public List<String> getPlacePhotos(List<Photo> photos) {
        List<String> uris = new ArrayList<>();
        for (Photo photo : photos) {
            PhotoURI response = placesClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("key", apiKey)
                    .queryParam("maxHeightPx", photo.heightPx())
                    .queryParam("maxWidthPx", photo.widthPx())
                    .queryParam("skipHttpRedirect", true)
                    .path("/v1/" + photo.name() + "/media")
                    .build())
                .retrieve()
                .bodyToMono(PhotoURI.class)
                .block();
            uris.add(response.photoUri());
        }
        return uris;
    }
}
