package com.angerasilas.health_system_backend.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctor_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = true)
    private String physicalAddress;

    @Column(nullable = true)
    private String street;

    @Column(nullable = true)
    private String town;

    @OneToMany(mappedBy = "doctorDetails")
    private List<ProgramEntity> createdPrograms;

    @OneToMany(mappedBy = "createdBy")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "registeredBy")
    private List<ClientDetails> registeredClients;
}
