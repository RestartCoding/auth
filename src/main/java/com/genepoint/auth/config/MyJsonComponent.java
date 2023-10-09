package com.genepoint.auth.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author xiabiao
 * @since 2023-10-09
 */
@JsonComponent
class MyJsonComponent {

//    static class StringSerializer extends JsonSerializer<String>{
//
//        @Override
//        public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
//            gen.writeString("i18n");
//        }
//    }

    /**
     * long 转为 string。防止js精度丢失
     */
    static class LongSerializer extends JsonSerializer<Long> {
        @Override
        public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(value.toString());
            }
        }
    }
}
