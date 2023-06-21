package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.*;

import java.util.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="customer")
@Builder
public class Customer extends UserEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mail", unique = true, nullable = false)
    private String mail;

    // HashSet: unordered List of *unique* elements
    // One to many because we have a CustomerSkill class as an Entity
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_skill",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date doB;

    @ManyToMany(mappedBy = "applicants")
    private List<Job> applications = new ArrayList<>();

    @OneToOne(mappedBy = "working")
    private Job currentJob;
}
