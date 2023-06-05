package de.thb.MACJEE.Service;

import de.thb.MACJEE.Repository.SkillRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;
}
