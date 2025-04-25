package com.angerasilas.health_system_backend.entity;

import lombok.*;

import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "client_details")
public class ClientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = true)
    private String physicalAddress;

    @Column(nullable = true)
    private String street;

    @Column(nullable = true)
    private String town;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "registered_by")
    private DoctorDetails registeredBy;

    @OneToMany(mappedBy = "client")
    private List<Enrollment> enrollments;
}
