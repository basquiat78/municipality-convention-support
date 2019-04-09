package com.kakaopay.municipality.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * Entity로는 원하는 형식으로 출력하기가 힘들다.
 * 필요하지 않는 컬럼도 전부 끌어오기 때문이다.
 * 
 * org.modelmapper:modelmapper를 써보고 싶었지만
 * 엔티티에서 지자체 코드 정보 자체도 오브젝트로 표현되버려서 이 방법은 버리고 그냥 매핑을 하기로 한다.
 * 사실 그런 경우 방법을 몰라서 그렇다.....
 * 
 * created by basquiat
 *
 */
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDto {
	
	/** unique id */
	private long id;
	
	/** 지차체 명 */
	private String municipalityName;
	
	/** 지원 대상 */
	private String supportTarget;
	
	/** 용도 */
	private String usage;
	
	/** 지원 한도 */
	private String supportLimit;
	
	/** 2차 보전 */
	private String rate;
	
	/** 추천기관 */
	private String recommandInstitute;
	
	/** 관리점 */
	private String management;
	
	/** 취급점 */
	private String reception;
	
}
