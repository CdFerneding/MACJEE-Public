package de.thb.MACJEE.Controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanySettingsForm {
    private String username;
    private String password;
    private String companyName;
    private String firstName;
    private String lastName;
    private String mail;
    private String description;
    private String address1;
    private String address2;
    private String country;
    private String state;
    private int zip;
    private String website;
    private String phoneNumber;
    private String jobTitle;
    private String jobDescription;
    private int jobSalary;
    private List<String> skillName;
    private List<Long> skillValue;
    private List<Boolean> skillHard;
    private Boolean isTemplate;
}
