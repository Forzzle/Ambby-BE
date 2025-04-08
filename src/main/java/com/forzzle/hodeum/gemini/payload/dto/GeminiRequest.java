package com.forzzle.hodeum.gemini.payload.dto;

import java.util.List;

public record GeminiRequest(List<Content> contents) {

    public record Content(List<Part> parts) {

        public record Part(String text) {

        }
    }
}
