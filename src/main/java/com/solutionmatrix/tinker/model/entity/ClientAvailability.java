package com.solutionmatrix.tinker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="clientavailability")
public class ClientAvailability implements Serializable {

    private static final long serialVersionUID = -1344220088903963901L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "date", nullable = false, unique = true)
    private String date;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "timeslot_id", nullable = false)
    private Long timeslotId;

    @Column(name = "status_id", nullable = false)
    private Long statusId;


}
