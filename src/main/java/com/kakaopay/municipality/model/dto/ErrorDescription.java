package com.kakaopay.municipality.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * error description model
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorDescription {

	/** 상태 코드 상세 정보 */
	private String status;
	
	/** 에러 메세지 */
	private String message;
	
}
