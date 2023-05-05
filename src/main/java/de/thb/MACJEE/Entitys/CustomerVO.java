package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.Characteristics;
import de.thb.MACJEE.Entitys.Enumerations.UserRole;

import java.util.List;

public class CustomerVO {
    private long id;
    private String mail;
    private List<Characteristics> characteristics;
    private UserRole userRole;

    public void applyForJob(JobVO job){

    }

}
