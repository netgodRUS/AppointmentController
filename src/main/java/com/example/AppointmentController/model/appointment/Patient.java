package com.example.AppointmentController.model.appointment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}