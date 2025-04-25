package com.angerasilas.health_system_backend.entity;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "programs_enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientDetails client;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private ProgramEntity program;

    @Column(name = "start_date", nullable = false) private String startDate;
    @Column(name = "end_date") private String endDate;

    @Column(nullable = false) private String status;
    @Column private String progress;
    @Column private String notes;

    @Column(name = "enrolled_at", nullable = false)
    private String enrolledAt;

    @ManyToOne
    @JoinColumn(name = "enrolled_by", nullable = false)
    private DoctorDetails createdBy;
}
