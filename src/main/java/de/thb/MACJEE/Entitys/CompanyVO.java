package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Enumerations.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyVO {
    private long id;
    private String name;
    private String description;
    private String location;
    private String mail;
    private long phoneNumber;
    private String website;
    private UserRole userRole;

    public JobVO uploadJob(Skill skillLevel) {
        // write Job into the Database
        // hand the new Job to the Controller
        return new JobVO();
    }
}
