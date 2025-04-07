package com.forzzle.hodeum.gemini.presentation;

import com.forzzle.hodeum.common.payload.Response;
import com.forzzle.hodeum.gemini.application.GeminiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
@Tag(name = "Gemini API", description = "Gemini 모델과의 상호작용 API<br>"
    + "해당 api는 테스트를 위한 용도입니다.(프론트에서 사용하지 않음)")
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping
    @Operation(summary = "Gemini 프롬프트 테스트")
    public ResponseEntity<?> askGemini(
        @RequestParam String prompt
    ) {
        String response = geminiService.askGemini(prompt);
        return Response.ok("success ask Gemini", response);
    }

}
