package de.thb.MACJEE.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class customerSkillsId implements Serializable {
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "skill_id")
    private Long skillId;
}
