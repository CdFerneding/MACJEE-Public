package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class CompanyVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    private String companyName;
    private String firstName;
    private String lastName;
    private String userName;

    @Column(name = "EMailAddress")
    private String mail;
    private String password;
    private String description;
    private String address;
    private String address2;
    private String country;
    private String state;

    @Column(name = "postalCode")
    private int zip;
    //private String phoneNumber;
    // Fehler beim Template - erstmal weglassen
    private String website;
    private UserRole userRole;

    public JobVO uploadJob(Skill skillLevel) {
        // write Job into the Database
        // hand the new Job to the Controller
        return new JobVO();
    }
}
