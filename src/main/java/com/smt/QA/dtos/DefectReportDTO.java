package com.smt.QA.dtos;

import jakarta.persistence.criteria.CriteriaBuilder;

public class DefectReportDTO {
    private Integer code; // Updated to String
    private String type;

    // Getters and Setters
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
