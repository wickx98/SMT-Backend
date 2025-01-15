package com.smt.QA.services;

import com.smt.QA.dtos.DefectDTO;
import com.smt.QA.entities.Defect;
import com.smt.QA.repositories.DefectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefectService {
    private final DefectRepository defectRepository;
    public DefectService(DefectRepository defectRepository) {
        this.defectRepository = defectRepository;
    }

    public Defect saveDefect(DefectDTO input){
        Defect defect = new Defect();
        defect.setCode(input.getCode());
        defect.setName(input.getName());
        defect.setDescription(input.getDescription());
        defect.setType(input.getType());
        return defectRepository.save(defect);
    }

    public List<Defect> getDefectByType(String type){

        return defectRepository.findByType(type);
    }

}
