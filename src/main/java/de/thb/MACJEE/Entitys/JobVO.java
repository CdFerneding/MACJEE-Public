package de.thb.MACJEE.Entitys;

import java.util.List;

public class JobVO {
    private long id;
    private String title;
    private List<Skill> requiredSkills;
    private long salary;
    private CompanyVO byCompany;
    private List<CustomerVO> applicants;
}
