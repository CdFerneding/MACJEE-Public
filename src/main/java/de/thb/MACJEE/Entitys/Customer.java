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

    @Transient
    private String pw_conf;

    // HashSet: unordered List of *unique* elements
    // One to many because we have a CustomerSkill class as an Entity
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_skill",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();

    @Temporal(TemporalType.DATE)
    private Date doB;

    @ManyToMany(mappedBy = "applicants")
    private Set<Job> jobs = new HashSet<>();
}
