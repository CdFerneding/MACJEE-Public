package de.thb.MACJEE.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Getter
@Setter
@Data
@Table(name = "Job")
@Entity
public class JobVO {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private List<Skill> requiredSkills;
    private long salary;
    private CompanyVO byCompany;
    private List<CustomerVO> applicants;
}
