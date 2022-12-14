package com.example.backendhome.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "flats")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Flat {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "city", length = 20)
    private String city;

    @NotNull
    @Column(name = "street", length = 20)
    private String street;

    @NotNull
    @Column(name = "houseNumber", length = 20)
    private String  houseNumber;

    @NotNull
    @Column(name = "entrance", length = 20)
    private Integer entrance;

    @NotNull
    @Column(name = "flatNumber", length = 20)
    private Integer flatNumber;

    @NotNull
    @Digits(integer = 7, fraction = 2)
    @Column(name = "area", precision = 9, scale = 2)
    private BigDecimal area;
}
