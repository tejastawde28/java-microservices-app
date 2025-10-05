package com.pm.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String address;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate registeredDate;

    public UUID getId() {
        return this.id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }

    public @NotNull @Email String getEmail() {
        return this.email;
    }

    public void setEmail(@NotNull @Email final String email) {
        this.email = email;
    }

    public @NotNull String getAddress() {
        return this.address;
    }

    public void setAddress(@NotNull final String address) {
        this.address = address;
    }

    public @NotNull LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(@NotNull final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotNull LocalDate getRegisteredDate() {
        return this.registeredDate;
    }

    public void setRegisteredDate(@NotNull final LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }
}
