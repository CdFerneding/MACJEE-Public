package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.*;

import java.util.*;

import lombok.*;
import org.springframework.security.core.userdetails.User;

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

    // HashSet: unordered List of *unique* elements
    @OneToMany(mappedBy = "customer")
    private Set<CustomerSkill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date doB;

    @ManyToMany(mappedBy = "employees")
    private Set<Job> jobs = new HashSet<>();

    public void applyForJob(Job job){

    }

}
