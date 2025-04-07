package com.forzzle.hodeum.gemini.application;

import com.forzzle.hodeum.gemini.payload.request.GeminiRequest;
import com.forzzle.hodeum.gemini.payload.response.GeminiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService(WebClient.Builder builder) {
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

}
