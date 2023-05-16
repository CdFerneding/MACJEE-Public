package de.thb.MACJEE.Entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_skill")
public class customerSkills {
    @EmbeddedId
    private customerSkillsId id;

    @ManyToOne
    @MapsId("customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("skill_id")
    private Skill skill;

    @Column(name = "is_hard_skill")
    private boolean isHardSkill;
}
