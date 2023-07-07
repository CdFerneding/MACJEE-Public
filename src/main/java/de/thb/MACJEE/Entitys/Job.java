package de.thb.MACJEE.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
    @ToString.Exclude
    private List<Skill> requiredSkills = new ArrayList<>();

    private int salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @ToString.Exclude
    private Company company;

    @Column(name = "is_open")
    private boolean isOpen;

    // if possible change: keep fetchType.LAZY for the applicants (I am getting LazyInitializationException for jobService.denyApplicant(...))
    @ManyToMany
    @JoinTable(name = "customer_job",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    @ToString.Exclude
    private List<Customer> applicants = new ArrayList<>();

    @OneToOne
    @ToString.Exclude
    private Customer working;

    @Override
    public String toString() {
        return this.getTitle() + " von der Firma: " + this.getCompany().getCompanyName();
    }
}
