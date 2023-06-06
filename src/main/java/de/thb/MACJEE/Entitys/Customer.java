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

    // ist für die Bestätigung
    // geht nicht in die DB
    @Transient
    private String pw_conf;

    // HashSet: unordered List of *unique* elements
    // One to many because we have a CustomerSkill class as an Entity
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<CustomerSkill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date doB;

    @ManyToMany(mappedBy = "applicants")
    private Set<Job> jobs = new HashSet<>();

}
