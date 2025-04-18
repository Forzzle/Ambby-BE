package com.forzzle.hodeum.gemini.payload.dto;

import java.util.List;

public record HumanTraffic(
    String summary,
    List<String> humanTraffic
) {

}
