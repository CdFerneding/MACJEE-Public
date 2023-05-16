package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.*;

import java.util.*;

import lombok.*;

@Getter
@Setter
@Data
@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "mail", unique = true, nullable = false)
    private String mail;

    private String password;

    // HashSet: unordered List of *unique* elements
    @OneToMany(mappedBy = "customer")
    private Set<Skill> skills = new HashSet<>();

    @Column(name = "user_role")
    private UserRole userRole;

    @Temporal(TemporalType.DATE)
    private Date doB;

    @ManyToMany(mappedBy = "employees")
    private Set<Job> jobs = new HashSet<>();

    public void applyForJob(Job job){

    }

}
