package com.solutionmatrix.tinker.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="appointment")
public class Appointment implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", updatable = false, insertable = false, nullable = false)
    @JsonBackReference("client_appointment_reference")
    @JsonIgnore
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeslot_id", updatable = false, insertable = false, nullable = false)
    @JsonBackReference("timeslot_appointment_reference")
    @JsonIgnore
    private Timeslot timeslot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", updatable = false, insertable = false, nullable = false)
    @JsonBackReference("status_appointment_reference")
    @JsonIgnore
    private Status status;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
