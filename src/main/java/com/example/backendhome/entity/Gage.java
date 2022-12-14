package com.example.backendhome.entity;

import com.example.backendhome.entity.enums.TypeGage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "gages", uniqueConstraints =
        @UniqueConstraint(columnNames = "serialNumber") )
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Gage {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "flat_id",
            referencedColumnName = "id")
    private Flat flat;

    @NotNull
    @Column(name = "serialNumber", length = 20)
    private String serialNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "typeGage", length = 30)
    private TypeGage typeGage;

    @NotNull
    @Column(name = "manufacturer", length = 20)
    private String manufacturer;

    @NotNull
    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "disposal_date")
    private LocalDate disposalDate;
}
