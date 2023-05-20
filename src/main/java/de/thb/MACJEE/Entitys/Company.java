package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(unique = true, nullable = false)
    private String mail;

    private String description;
    private String address1;
    private String address2;
    private String country;
    private String state;

    @Column(name = "postal_code")
    private int zip;

    //private String phoneNumber;
    // Fehler beim Template - erstmal weglassen
    @Column(unique = true)
    private String website;

    private String password;

    @ManyToMany
    @JoinTable(name = "company_roles",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    // bidirectional --> no according column in the database needed, just visible in JPA
    @OneToMany(mappedBy = "byCompany")
    private List<Job> jobs;

    public Job uploadJob(Skill characteristicsLevel) {
        // write Job into the Database
        // hand the new Job to the Controller
        return new Job();
    }
}
