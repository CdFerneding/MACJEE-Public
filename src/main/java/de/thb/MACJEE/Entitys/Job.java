package de.thb.MACJEE.Entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    // HashSet: unordered List of *unique* elements
    @ManyToMany
    @JoinTable(name = "job_skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> requiredSkills = new ArrayList<>();

    private int salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "is_open")
    private boolean isOpen;

    @ManyToMany
    @JoinTable(name = "customer_job",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> applicants = new ArrayList<>();
}
