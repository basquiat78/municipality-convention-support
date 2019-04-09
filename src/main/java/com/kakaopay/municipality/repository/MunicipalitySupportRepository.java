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
public interface MunicipalitySupportRepository extends JpaRepository<MunicipalitySupport, Long> {
	
	MunicipalitySupport findById(long id);
	
	/**
	 * like 검색을 우아하고 아름답고 간지나게....
	 * @param municipalityName
	 * @return List<MunicipalitySupport>
	 */
	List<MunicipalitySupport> findByMunicipalityMunicipalityNameContaining(String municipalityName);
}
