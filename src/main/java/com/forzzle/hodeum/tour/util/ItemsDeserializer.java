package com.forzzle.hodeum.tour.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.forzzle.hodeum.tour.payload.TourPlacePreiew;
import com.forzzle.hodeum.tour.payload.TourPlacePreiew.Response.Body.Items;
import com.forzzle.hodeum.tour.payload.TourPlacePreiew.Response.Body.Items.Item;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ItemsDeserializer extends JsonDeserializer<Items> {

    @Override
    public Items deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        // items: "" 처리를 위해
        if (node.getNodeType() == JsonNodeType.STRING && node.asText().isEmpty()) {
            return new Items(Collections.emptyList());
        }

        // 정상적인 케이스: item 필드에서 직접 꺼내기
        JsonNode itemNode = node.get("item");
        ObjectMapper mapper = (ObjectMapper) p.getCodec();

        List<Item> items = Collections.emptyList();
        if (itemNode != null) {
            if (itemNode.isArray()) {
                items = mapper.readerForListOf(Item.class).readValue(itemNode);
            } else {
                // 단일 객체가 올 수도 있음
                Item singleItem = mapper.treeToValue(itemNode, Item.class);
                items = List.of(singleItem);
            }
        }

        return new Items(items);
    }
}