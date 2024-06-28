package com.hameed.springboot.pharmacyms.config;

import com.hameed.springboot.pharmacyms.util.StringToCategoryConverter;
import com.hameed.springboot.pharmacyms.util.StringToDateConverter;
import com.hameed.springboot.pharmacyms.util.StringToUnitOfMeasureConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private StringToUnitOfMeasureConverter stringToUnitOfMeasureConverter;
    private StringToCategoryConverter stringToCategoryConverter;
    private StringToDateConverter stringToDateConverter;

    @Autowired
    public WebConfig(StringToUnitOfMeasureConverter stringToUnitOfMeasureConverter, StringToCategoryConverter stringToCategoryConverter, StringToDateConverter stringToDateConverter) {
        this.stringToUnitOfMeasureConverter = stringToUnitOfMeasureConverter;
        this.stringToCategoryConverter = stringToCategoryConverter;
        this.stringToDateConverter = stringToDateConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToUnitOfMeasureConverter);
        registry.addConverter(stringToCategoryConverter);
        registry.addConverter(stringToDateConverter);
    }
}
