package com.app.emsx.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> doctors = new ArrayList<>();
}
