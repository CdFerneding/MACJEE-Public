package de.thb.MACJEE.Entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    private int ability;

    @OneToMany(mappedBy = "skill")
    private Set<CustomerSkill> customers = new HashSet<>();

    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Job> jobs = new HashSet<>();
}
