package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.Characteristics;
import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import java.util.List;

@Data
@Entity
@Table(name="Customer")
public class CustomerVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    private String firstName;
    private String lastName;
    private String userName;

    @Column(name = "mail")
    private String mail;
    private List<Characteristics> characteristics;
    @Column(name = "userrole")
    private UserRole userRole;

    public void applyForJob(JobVO job){

    }

}
