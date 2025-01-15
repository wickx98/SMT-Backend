package com.smt.QA.repositories;

import com.smt.QA.entities.Defect;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefectRepository extends  CrudRepository<Defect, Integer>{

    List<Defect> findByType(String type);
}




