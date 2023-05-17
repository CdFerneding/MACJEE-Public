package de.thb.MACJEE.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class CustomerSkillId implements Serializable {
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "skill_id")
    private Long skillId;
}