package com.forzzle.hodeum.gemini.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forzzle.hodeum.gemini.payload.dto.GeminiRequest;
import com.forzzle.hodeum.gemini.payload.dto.GeminiResponse;
import com.forzzle.hodeum.gemini.payload.dto.HumanTraffic;
import com.forzzle.hodeum.gmap.payload.dto.GoogleMapPlaceDetail.Review;
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

    public String[] getSoundList(String placeName) {
        StringBuilder prompt = new StringBuilder("주어진 장소를 보고, 사운드 리스트에서 가장 적합한 사운드들을 추천해줘.\n"
            + "이때, 다음 조건을 지켜줘.\n"
            + "1. 출력은 *메타문장을 제거*하고, \',\'로 구분된(공백 및 개행 제거) 사운드 리스트를 반환해줘. 예) beach,wind"
            + "2. 최소 1개에서 최대 3개의 사운드를 추천해줘.\n"
            + "사운드 리스트는 다음과 같아."
            + "[crowd,beach,wind,eat,footstep]\n"
            + "장소는" + placeName + "이야.");
        String answer = askGemini(prompt.toString());

        //문장 마지막 개행 제거
        String preProcessed = answer.replace("\n", "");
        String[] soundList = preProcessed.split(",");
        return soundList;
    }

    public HumanTraffic getHumanTraffic(List<String> places) {
        StringBuilder prompt = new StringBuilder("""
            내일 하루 동안 아래 장소들로 진짜 여행간다고 생각하고 최적화된 동선 짜서 알려줘.
            음식점 같은 경우는 아침, 점심, 저녁에 먹을거니까 잘 배치 해주고 디저트나 액티비티 같은 경우도 알아서 잘 센스있게 배치해줘.
            그리고 이때 음식점이랑 장소가 비슷하다 해서 **묶지 말고 정확히** 제시해줘.
            
            **쓸데없는 문장은 빼고** 다음과 같은 **JSON형식을 꼭 지켜서** 대답해줘.
            **humanTraffic에 음식점같은 가게도 꼭 포함시켜**
            {
              "summary" : "동선 결정 근거 요약",
              "humanTraffic" : ["장소 또는 가게1", "장소 또는 가게2"]
            }
            
            [장소 리스트]
            """);
        for (String place : places) {
            prompt.append(place).append("\n");
        }
        String answer = askGemini(prompt.toString());
        String cleaned = answer.replaceAll("^```json\\s*", "")  // 앞의 ```json 및 개행 제거
            .replaceAll("\\s*```$", "");

        ObjectMapper objectMapper = new ObjectMapper();
        HumanTraffic humanTraffic;
        try {
            humanTraffic = objectMapper.readValue(cleaned, HumanTraffic.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON parsing error: " + cleaned, e);
        }
        return humanTraffic;
    }
}
