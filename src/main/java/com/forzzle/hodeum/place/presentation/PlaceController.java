package com.forzzle.hodeum.place.presentation;

import com.forzzle.hodeum.common.payload.Response;
import com.forzzle.hodeum.gmap.payload.response.PlacePreviewResponse;
import com.forzzle.hodeum.place.application.PlaceService;
import com.forzzle.hodeum.place.payload.response.PlaceDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping(value = "/search")
    @Operation(summary = "여행지 검색",
        description = "여행지를 검색합니다.<br>"
            + "검색어는 자유롭게 입력하여도 됩니다.<br>"
            + "20개의 장소를 반환합니다."
    )
    public ResponseEntity<?> searchPlaces(
        @RequestBody String query
    ) {
        List<PlacePreviewResponse> response = placeService.searchPlaces(query);
        return Response.ok("success search places", response);
    }

    @PostMapping(value = "/detail/{placeId}")
    @Operation(summary = "여행지 상세정보",
        description = "여행지의 상세정보를 조회합니다."
    )
    public ResponseEntity<?> getPlaceDetail(
        @PathVariable String placeId
    ) {
        PlaceDetailResponse response = placeService.getPlaceDetail(placeId);
        return Response.ok("success get detail of place.", response);
    }

}
