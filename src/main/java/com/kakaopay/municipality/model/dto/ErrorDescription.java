package com.kakaopay.municipality.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * error description model
 * 
 * created by basquiat
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorDescription {

	/**
	 * 필요한 필드만 빌드 패턴으로 사용한다.
	 * @param status
	 * @param message
	 */
	@Builder
	public ErrorDescription(String status, String message){
	    this.status = status;
	    this.message = message;
	}
	
	/**
	 * 상태 코드 상세 정보
	 */
	private String status;
	
	/**
	 * 에러 메세지
	 */
	private String message;
	
}
