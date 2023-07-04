package de.thb.MACJEE.Entitys;

import jakarta.persistence.*;
import lombok.*;
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

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    private List<Job> jobs;

    public void addJob(Job job){
        jobs.add(job);
    }
}
