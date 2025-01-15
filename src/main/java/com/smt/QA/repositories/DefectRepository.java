package com.smt.QA.repositories;

import com.smt.QA.entities.Defect;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DefectRepository extends  CrudRepository<Defect, Integer>{

    List<Defect> findByType(String type);
}




