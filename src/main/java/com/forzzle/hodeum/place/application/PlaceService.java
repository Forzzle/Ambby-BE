package com.forzzle.hodeum.place.application;

import com.forzzle.hodeum.gemini.application.GeminiClient;
import com.forzzle.hodeum.gemini.payload.dto.HumanTraffic;
import com.forzzle.hodeum.gmap.application.GoogleMapClient;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail;
import com.forzzle.hodeum.gmap.payload.dto.GooglePlacePreview;
import com.forzzle.hodeum.gmap.payload.dto.GooglePlacePreview.Place;
import com.forzzle.hodeum.gmap.payload.response.PlacePreviewResponse;
import com.forzzle.hodeum.place.payload.dto.ToggleDetail;
import com.forzzle.hodeum.place.payload.request.HumanTrafficRequest;
import com.forzzle.hodeum.place.payload.response.PlaceDetailResponse;
import com.forzzle.hodeum.place.payload.response.PlacePreviewsResponse;
import com.forzzle.hodeum.tour.application.TourClient;
import com.forzzle.hodeum.tour.payload.TourPlaceDetail;
import com.forzzle.hodeum.tour.payload.TourPlacePreiew;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final GoogleMapClient googleMapClient;
    private final GeminiClient geminiClient;
    private final TourClient tourClient;

    public PlacePreviewsResponse searchPlaces(String query, String pageToken) {

        GooglePlacePreview placePreviews = googleMapClient.searchPlaces(query, pageToken);

        PlacePreviewsResponse response = makePlacePreviewResponses(placePreviews);

        return response;
    }

    private PlacePreviewsResponse makePlacePreviewResponses(GooglePlacePreview response) {
        List<Place> places = response.places();
        List<PlacePreviewResponse> result = new ArrayList<>();
        for (Place place : places) {
            String address = googleMapClient.searchAddressWithCoordinate(
                place.location().latitude(),
                place.location().longitude());
            result.add(
                new PlacePreviewResponse(
                    place.id(),
                    place.displayName().text(),
                    address,
                    place.primaryTypeDisplayName() != null ? place.primaryTypeDisplayName()
                        .text() : null
                )
            );
        }

        return new PlacePreviewsResponse(result, response.nextPageToken());
    }

    public PlaceDetailResponse getPlaceDetail(String placeId) {
        GoogleMapPlaceDetail googleMapPlaceDetail = googleMapClient.getPlaceDetail(placeId);
        String summary = geminiClient.getSummary(googleMapPlaceDetail.reviews());
        String[] soundList = geminiClient.getSoundList(googleMapPlaceDetail.displayName().text());
        TourPlacePreiew tourPlacePreview = tourClient.getPlaceIdWithLocation(
            googleMapPlaceDetail.location());

        if (tourPlacePreview.isEmpty()) {
            return new PlaceDetailResponse(googleMapPlaceDetail, summary, soundList, null);
        }
        TourPlaceDetail tourPlaceDetail = tourClient.getPlaceDetail(
            tourPlacePreview.getContentId());
        ToggleDetail toggleDetail = ToggleDetail.of(googleMapPlaceDetail, tourPlaceDetail);
        return new PlaceDetailResponse(googleMapPlaceDetail, summary, soundList, toggleDetail);
    }

    public HumanTraffic getHumanTraffic(HumanTrafficRequest request) {
        List<String> places = request.places();
        HumanTraffic response = geminiClient.getHumanTraffic(places);
        return response;
    }
}
