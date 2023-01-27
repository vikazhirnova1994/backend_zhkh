package com.example.backendhome.service.generator;

import com.example.backendhome.dto.request.Date;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateTransform {
    public LocalDate toTransform(Date date) {
        return LocalDate.of(
                Integer.valueOf(date.getYear()),
                Integer.valueOf(date.getMonth()),
                Integer.valueOf(date.getDay()));
    }
}
