package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class CompanyVO {
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String password;
    private String description;
    private String address;
    private String address2;
    private String country;
    private String state;
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
