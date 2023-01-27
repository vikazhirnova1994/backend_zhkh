package com.example.backendhome.service.generator;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateGenerator {
    private final static LocalDate NOW = LocalDate.now();
    private final static int DATE_FROM = 20;
    private final static int DATE_TO = 25;

    public LocalDate fromCurrentMonth(){
        if (NOW.getDayOfMonth() >= DATE_FROM && NOW.getDayOfMonth() <= NOW.getMonth().maxLength()) {
            return currentMonthLocalDate(DATE_FROM);
        }
        return previousMonthLocalDate(DATE_FROM);
    }

    public LocalDate toCurrentMonth(){
       if (NOW.getDayOfMonth() >= DATE_FROM && NOW.getDayOfMonth() <= NOW.getMonth().maxLength()) {
            return currentMonthLocalDate(DATE_TO);
        }
        return previousMonthLocalDate(DATE_TO);
    }

    public LocalDate fromPreviousMonth(){
        return previousMonthLocalDate(DATE_FROM);
    }

    public LocalDate toPreviousMonth(){
        return previousMonthLocalDate(DATE_TO);
    }


    private LocalDate previousMonthLocalDate(int dayOfMonth){
        return NOW.getMonth().getValue() != 1
                ? LocalDate.of(NOW.getYear(), (NOW.getMonth().getValue() - 1), dayOfMonth)
                : LocalDate.of(NOW.getYear()-1, 12, dayOfMonth);
    }

    private LocalDate currentMonthLocalDate(int dayOfMonth){
        return LocalDate.of(NOW.getYear(), (NOW.getMonth().getValue()), dayOfMonth);
    }

    public int getDateTo(){
        return DATE_TO;
    }

    public int getDateFrom(){
        return DATE_FROM;
    }
}
