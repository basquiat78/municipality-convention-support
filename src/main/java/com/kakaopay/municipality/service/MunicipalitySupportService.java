package com.kakaopay.municipality.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kakaopay.municipality.model.MunicipalitySupport;
import com.kakaopay.municipality.model.dto.ResponseDto;
import com.kakaopay.municipality.repository.MunicipalitySupportRepository;
import com.kakaopay.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * 
 * MunicipalityService에서 직접적으로 MunicipalityRepository를 DI하지 말고
 * 
 * Service를 통해서 액션을 취하는 형식으로 가기 위해서 Service를 따로 둔다.
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("municipalitySupportService")
public class MunicipalitySupportService {
	
	@Autowired
	private MunicipalitySupportRepository municipalitySupportRepository;
	
	/**
	 * 
	 * save All
	 * 
	 * @param municipalitySupports
	 */
	public void saveAll(List<MunicipalitySupport> municipalitySupports) {
		log.info("save all MunicipalitySupport List");
		municipalitySupportRepository.saveAll(municipalitySupports);
	}
	
	/**
	 * 
	 * not paging
	 * 
	 * @return Flux<ResponseDto>
	 */
	public Flux<ResponseDto> findAllWithoutPaging() {
		return Flux.fromIterable(CommonUtil.convertMunicipalitySupportListFromEntity(municipalitySupportRepository.findAll()));
	}
	
	/**
	 * 
	 * with paging
	 * 
	 * @param pageable
	 * @return Flux<ResponseDto>
	 */
	public Flux<ResponseDto> findAllWithPaging(Pageable pageable) {
		return Flux.fromIterable(CommonUtil.convertMunicipalitySupportListFromEntity(municipalitySupportRepository.findAll(pageable).stream().collect(Collectors.toList())));
	}
	
	/**
	 * search municipalitySupport with municipalityName
	 * 
	 * like 검색으로
	 * 
	 * oneToone이지만 만일 '충청'으로 검색하게 되면 리스트로 나오게 된다.
	 * 따라서 리스트로 받아치자!
	 * 
	 * @param municipalityName
	 * @return Flux<ResponseDto>
	 */
	public Flux<ResponseDto> findByMunicipalityName(String municipalityName) {
		return Flux.fromIterable(CommonUtil.convertMunicipalitySupportListFromEntity(municipalitySupportRepository.findByMunicipalityMunicipalityNameContaining(municipalityName)));
	}
	
}
