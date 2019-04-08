package com.kakaopay.municipality.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.municipality.model.MunicipalitySupport;

/**
 * 
 * MunicipalitySupportRepository
 * 
 * created by basquiat
 *
 */
public interface MunicipalitySupportRepository extends JpaRepository<MunicipalitySupport, String> {
	List<MunicipalitySupport> findByMunicipalityMunicipalityNameContaining(String municipalityName);
}
