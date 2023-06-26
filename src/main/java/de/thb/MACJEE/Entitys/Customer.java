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
    // Not yet used for simplicity
    @ManyToMany
    @JoinTable(name = "customer_skill",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @ToString.Exclude
    private List<Skill> skills = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date doB;

    @ManyToMany(mappedBy = "applicants")
    @ToString.Exclude
    private List<Job> applications = new ArrayList<>();

    @OneToOne(mappedBy = "working")
    private Job currentJob;

    public void addSkill(Skill skill){
        skills.add(skill);
    }
}
