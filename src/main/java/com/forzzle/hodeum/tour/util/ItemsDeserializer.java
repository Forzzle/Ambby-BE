package com.forzzle.hodeum.tour.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.forzzle.hodeum.tour.payload.TourPlacePreiew.Response.Body.Items;
import java.io.IOException;
import java.util.Collections;

public class ItemsDeserializer extends JsonDeserializer<Items> {

    @Override
    public Items deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        // items: "" 처리를 위해
        if (node.getNodeType() == JsonNodeType.STRING && node.asText().isEmpty()) {
            return new Items(Collections.emptyList());
        }

        // 일반적인 경우는 Jackson으로 delegate
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        return mapper.treeToValue(node, Items.class);
    }
}