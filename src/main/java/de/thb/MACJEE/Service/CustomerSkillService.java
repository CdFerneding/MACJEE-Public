package de.thb.MACJEE.Service;

import de.thb.MACJEE.Repository.CustomerSkillRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class CustomerSkillService {

    @Autowired
    private CustomerSkillRepository customerSkillRepository;
}
