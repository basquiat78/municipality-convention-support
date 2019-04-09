package com.kakaopay.municipality.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 이렇게까지 dto를 만들어야할까? 일단 원하는 형식으로는 내보내야하니...
 * 
 * 지원한도, 2차보전 오름차순/내림차순으로 나온 name들을 보여주기 위한 DTO
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MunicipalityNamesDto {
	
	/** 지차체 명들 */
	private String municipalityNames;
	
}
