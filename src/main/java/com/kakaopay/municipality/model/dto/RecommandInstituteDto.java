package com.kakaopay.municipality.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * rate가 가장 낮은 추천기관을 찾기 위한 dto
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RecommandInstituteDto {

	/** 추천기관명 */
	private String recommandInstitute;
	
	/** min rate */
	private double minRate;

}
