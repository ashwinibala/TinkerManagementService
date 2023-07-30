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
@Table(name="timeslot")
public class Timeslot implements Serializable {

    private static final long serialVersionUID = -1344220088903963901L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "starttime", nullable = false, unique = true)
    private String startTime;

    @Column(name = "endtime", nullable = false)
    private String endTime;


}
