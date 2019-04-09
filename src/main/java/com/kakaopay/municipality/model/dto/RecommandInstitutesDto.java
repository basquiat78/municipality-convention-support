package com.kakaopay.municipality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * rate가 가장 낮은 추천기관을 위한 Dto
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecommandInstitutesDto {

	/** 추천기관명 들 */
	private String recommandInstitutes;

}
