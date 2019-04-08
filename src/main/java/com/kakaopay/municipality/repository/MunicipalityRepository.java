package com.kakaopay.municipality.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.municipality.model.Municipality;

/**
 * 
 * MunicipalityRepository
 * 
 * created by basquiat
 *
 */
public interface MunicipalityRepository extends JpaRepository<Municipality, String> {
}
