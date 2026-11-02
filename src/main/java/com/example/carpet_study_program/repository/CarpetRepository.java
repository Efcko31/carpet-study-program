package com.example.carpet_study_program.repository;

import com.example.carpet_study_program.entity.CarpetProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarpetRepository extends JpaRepository<CarpetProduct, Long> {
}
