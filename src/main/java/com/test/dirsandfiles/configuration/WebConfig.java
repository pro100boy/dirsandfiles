package com.test.dirsandfiles.configuration;

import com.test.dirsandfiles.util.JacksonObjectMapper;
import com.test.dirsandfiles.util.formatter.DateTimeFormatters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customStringHttpMessageConverter());
        converters.add(customJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public StringHttpMessageConverter customStringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(
                Arrays.asList(
                        new MediaType("text", "plain", Charset.forName("UTF-8")),
                        new MediaType("text", "html", Charset.forName("UTF-8")))
        );
        return stringHttpMessageConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(JacksonObjectMapper.getMapper());
        return jsonConverter;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalDateTimeFormatter());
        super.addFormatters(registry);
    }
}
