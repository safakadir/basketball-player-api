package com.safakadir.basketballplayerapi.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safakadir.basketballplayerapi.model.Player;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PlayerJsonConverter implements AttributeConverter<Player, String> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Player jsonObj) {
            try {
                return objectMapper.writeValueAsString(jsonObj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to serialize object to JSON string", e);
            }
        }

        @Override
        public Player convertToEntityAttribute(String jsonStr) {
            try {
                return objectMapper.readValue(jsonStr, Player.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to deserialize object from JSON string", e);
            }
        }
}
