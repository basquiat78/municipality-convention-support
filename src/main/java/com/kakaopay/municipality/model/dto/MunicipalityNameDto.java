package com.kakaopay.municipality.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 지원한도, 2차보전을 위한 dto
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MunicipalityNameDto {

	/**
	 * 해당 이름을 ,자로 붙여서 최종 갯수만큼 조합한다.
	 */
	private String municipalityName;
	
	/**
	 * amount는 지원한도의 한글액수를 실제 액수로 변환할 것이다.
	 */
	private long amount;
	
	/**
	 * rate이기 때문에 0.00같은 표현이 필요하디 double로 하자.
	 */
	private double averageRateLimit;
	
}
