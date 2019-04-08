package com.kakaopay.municipality.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Csv file 정보를 매핑할 Dto
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CsvDto {

	/**
	 * id
	 */
	private long id;
	
	/**
	 * 지자체 코드
	 */
	private String municipalityName;
	
	/**
	 * 지원 대상
	 */
	private String supportTarget;
	
	/**
	 * 용도
	 */
	private String usage;
	
	/**
	 * 지원 한도
	 */
	private String supportLimit;
	
	/**
	 * 2차 보전
	 */
	private String rate;
	
	/**
	 * 추천기관
	 */
	private String recommandInstitute;
	
	/**
	 * 관리점
	 */
	private String management;
	
	/**
	 * 취급점
	 */
	private String reception;
	
}
