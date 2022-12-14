package com.example.backendhome.entity;

import com.example.backendhome.entity.enums.ClaimStatus;
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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "claims")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;


    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id")
    private User user;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "executor_identification_number", length = 10)
    private String executorIdentificationNumber;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private ClaimStatus status;
}
