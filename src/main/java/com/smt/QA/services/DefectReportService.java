package com.smt.QA.services;

import com.smt.QA.dtos.DefectReportDTO;
import com.smt.QA.entities.DefectReport;
import com.smt.QA.repositories.DefectReportRepository;
import org.springframework.stereotype.Service;

@Service
public class DefectReportService {

    private final DefectReportRepository defectReportRepository;

    public DefectReportService(DefectReportRepository defectReportRepository) {
        this.defectReportRepository = defectReportRepository;
    }

    public DefectReport saveDefectReport(DefectReportDTO input , String username){
        DefectReport defectReport= new DefectReport();
        defectReport.setCode(input.getCode());
        defectReport.setType(input.getType());
        defectReport.setReporter(username);
        return defectReportRepository.save(defectReport);
    }
}
