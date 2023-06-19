package de.thb.MACJEE.Service;

import de.thb.MACJEE.Entitys.Role;
import de.thb.MACJEE.Repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Data
@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
