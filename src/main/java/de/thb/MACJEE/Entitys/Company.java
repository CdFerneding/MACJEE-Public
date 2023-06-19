package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "company")
@Builder
public class Company extends UserEntity {

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String mail;

    // ist für die Bestätigung
    // geht nicht in die DB
    @Transient
    private String pw_conf;
    private String description;
    private String address1;
    private String address2;
    private String country;
    private String state;

    @Column(name = "postal_code")
    private int zip;

    private String phoneNumber;

    @Column(unique = true)
    private String website;

    // bidirectional --> no according column in the database needed, just visible in JPA
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Job> jobs;

    public Job uploadJob(Skill characteristicsLevel) {
        // write Job into the Database
        // hand the new Job to the Controller
        return new Job();
    }
}
