package de.thb.MACJEE.Controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterCompanyForm {
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
}
