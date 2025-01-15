package com.smt.QA.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "defect_reports")
public class DefectReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the report

    @Column(name = "code", nullable = false)
    private Integer code; // Defect code

    @Column(name = "type", nullable = false)
    private String type; // Defect type

    @Column(name = "reporter", nullable = false)
    private String reporter; // Name of the person reporting the defect

    @Column(name = "reported_at", nullable = false ,updatable = false)
    @CreationTimestamp
    private Date reportAt; // Timestamp when the defect was reported

    // Constructors
    public DefectReport() {}

    public DefectReport(Integer code, String type, String reporter, Date reportAt) {
        this.code = code;
        this.type = type;
        this.reporter = reporter;
        this.reportAt = reportAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public Date getReportAt() {
        return reportAt;
    }

    public void setReportAt(Date reportAt) {
        this.reportAt = reportAt;
    }
}
