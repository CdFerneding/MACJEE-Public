package de.thb.MACJEE.Entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    private Long level;

    @ManyToMany(mappedBy = "skills")
    @ToString.Exclude
    private List<Customer> customers = new ArrayList<>();

    // Wrapper class allows attribute to be null
    @Column(name = "is_hard_skill")
    private Boolean isHardSkill;

    @ManyToMany(mappedBy = "requiredSkills")
    @ToString.Exclude
    private List<Job> jobs = new ArrayList<>();
}
