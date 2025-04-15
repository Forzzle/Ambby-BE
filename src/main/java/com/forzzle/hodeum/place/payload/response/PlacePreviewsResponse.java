package com.forzzle.hodeum.place.payload.response;

import com.forzzle.hodeum.gmap.payload.response.PlacePreviewResponse;
import java.util.List;

public record PlacePreviewsResponse(
    List<PlacePreviewResponse> previews,
    String nextPageToken
) {

}
