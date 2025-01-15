package com.smt.QA.controllers;

import com.smt.QA.dtos.DefectDTO;
import com.smt.QA.dtos.DefectReportDTO;
import com.smt.QA.entities.Defect;
import com.smt.QA.entities.DefectReport;
import com.smt.QA.services.DefectReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class DefectReportController {
    private final DefectReportService defectReportService;

    public DefectReportController(DefectReportService defectReportService) {
        this.defectReportService = defectReportService;
    }

    @PostMapping("/defectsReport")
    public ResponseEntity<DefectReport> saveDefectReport(@RequestBody DefectReportDTO defectReportDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Request made by: " + username);
        DefectReport saveDefectReport = defectReportService.saveDefectReport(defectReportDTO , username);
        return ResponseEntity.ok(saveDefectReport);
    }
}
