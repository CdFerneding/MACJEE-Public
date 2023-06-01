package de.thb.MACJEE.Entitys;

import de.thb.MACJEE.Entitys.Enumerations.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    private String name;

    // roles to customer and company do not need to be bidirectional
}
