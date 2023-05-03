package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Enumerations.Characteristics;
import de.thb.MACJEE.Enumerations.UserRole;

import java.util.List;

public class CustomerVO {
    private long id;
    private String mail;
    private List<Characteristics> cahracteristics;
    private UserRole userRole;

    public void applyForJob(JobVO job){

    }

}
