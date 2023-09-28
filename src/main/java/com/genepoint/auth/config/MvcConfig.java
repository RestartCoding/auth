package com.genepoint.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
    }

    class DateFormatter implements Formatter<Date>{

        @Override
        public Date parse(String text, Locale locale) throws ParseException {
            return new Date(Long.parseLong(text));
        }

        @Override
        public String print(Date object, Locale locale) {
            return String.valueOf(object.getTime());
        }
    }
}
