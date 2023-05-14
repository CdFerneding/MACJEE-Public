package de.thb.MACJEE.Entitys;

import jakarta.persistence.Entity;
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
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // HashSet: unordered List of *unique* elements
    @ManyToMany
    @JoinTable(name = "job_skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> requiredSkills = new HashSet<>();

    private int salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company byCompany;

    private boolean is_open;

    @ManyToMany
    @JoinTable(name = "customer_job",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Customer> employees = new HashSet<>();


}
