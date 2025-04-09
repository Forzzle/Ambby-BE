package com.forzzle.hodeum.gemini.application;

import com.forzzle.hodeum.gemini.payload.dto.GeminiRequest;
import com.forzzle.hodeum.gemini.payload.dto.GeminiResponse;
import com.forzzle.hodeum.gmap.payload.dto.PlaceDetail.Review;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiClient {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://generativelanguage.googleapis.com/v1beta/models")
            .build();
    }

    public String askGemini(String prompt) {
        GeminiRequest request = new GeminiRequest(List.of(
            new GeminiRequest.Content(List.of(
                new GeminiRequest.Content.Part(prompt)
            ))
        ));

        GeminiResponse response = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/gemini-2.0-flash-lite:generateContent")
                .queryParam("key", apiKey)
                .build())
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(GeminiResponse.class)
            .block();

        return response.candidates().get(0).content().parts().get(0).text();
    }

    public String getSummary(List<Review> reviews) {
        StringBuilder prompt = new StringBuilder("다음 리뷰들을 요약해줘. 이때, 다음 조건을 지켜줘.\n "
            + "1.정보제공에 있어서 불필요한 메타문장은 제거\n"
            + "2.장점, 단점 정리(리뷰에 해당 내용이 없을시 제거)\n"
            + "3.다음 형식을 지켜줘. ## 리뷰 요약\\n\\n**장점:**\\n\\n*   가격이 저렴함.\\n\\n**단점:**\\n\\n*   맛의 기복이 있음.\n"
            + "리뷰 데이터는 다음과 같아.");
        for (Review review : reviews) {
            prompt.append("리뷰: ").append(review.text().text()).append("\n");
        }
        return askGemini(prompt.toString());
    }
}
