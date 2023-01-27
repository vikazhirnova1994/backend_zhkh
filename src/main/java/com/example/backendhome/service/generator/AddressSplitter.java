package com.example.backendhome.service.generator;

import org.springframework.stereotype.Component;

@Component
public class AddressSplitter {

    public String getCity(String address){
        String[] split = address.split(", ");
        return split[0];
    }

    public String getStreet(String address){
        String[] split = address.split(", ");
        return getValue(split[1]);
    }

    public String getHouseNumber(String address){
        String[] split = address.split(", ");
        return getValue(split[2]);
    }


    public Integer getEntrance(String address){
        String[] split = address.split(", ");
        return Integer.valueOf(getValue(split[3]));
    }

    public Integer getFlatNumber(String address){
        String[] split = address.split(", ");
        return  Integer.valueOf(getValue(split[4]));
    }

    private String getValue(String arg) {
        return arg.split(" ")[1];
    }
}
