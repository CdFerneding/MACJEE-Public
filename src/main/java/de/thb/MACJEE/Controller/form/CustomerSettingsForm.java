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
public class CustomerSettingsForm {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String doB;
    private String mail;
    private String skill;
    private List<Integer> value;
}
