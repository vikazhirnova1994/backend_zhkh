package com.example.backendhome.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "contract")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contract {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "contract_number", length = 20)
    private String contractNumber;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "flat_id",
            referencedColumnName = "id")
    private Flat flat;

    @NotNull
    @Column(name = "signed_date")
    private LocalDate signedDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> user = new ArrayList<>();
}
