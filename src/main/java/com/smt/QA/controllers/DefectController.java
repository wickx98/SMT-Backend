package com.smt.QA.controllers;

import com.smt.QA.dtos.DefectDTO;
import com.smt.QA.entities.Defect;
import com.smt.QA.services.DefectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class DefectController {

    private final DefectService defectService;

    public DefectController(DefectService defectService) {
        this.defectService = defectService;
    }
    @PostMapping("/defects")
    public ResponseEntity<Defect> saveDefect(@RequestBody DefectDTO defectDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Request made by: " + username);
        Defect saveDefect = defectService.saveDefect(defectDTO);
        return ResponseEntity.ok(saveDefect);
    }

    // Get defects by type
    @GetMapping("/defects")
    public ResponseEntity<List<Defect>> getDefectsByType(@RequestParam String type) {
        System.out.println("Received type: " + type);  // Add logging to see if type is passed correctly
        List<Defect> defects = defectService.getDefectByType(type);
        return ResponseEntity.ok(defects);
    }

}
