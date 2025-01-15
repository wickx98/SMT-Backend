package com.smt.QA.repositories;


import com.smt.QA.entities.DefectReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefectReportRepository extends CrudRepository<DefectReport, Integer>{
}



