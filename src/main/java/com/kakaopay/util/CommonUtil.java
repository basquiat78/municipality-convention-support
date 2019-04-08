package com.kakaopay.util;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.municipality.model.MunicipalitySupport;
import com.kakaopay.municipality.model.dto.ResponseDto;

/**
 * 
 * Common Utility
 * 
 * created by basquiat
 *
 */
public class CommonUtil {
	
	/**
	 * json format string -> Mapping Object
	 * 
	 * @param content
	 * @param clazz
	 * @return T
	 * @throws Exception
	 */
	public static <T> T convertObjectFromJsonString(String content, Class<T> clazz) {
		
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = (T) mapper.readValue(content, clazz);
		} catch (Exception e) {
			// 상위 핸들러에서 에러 위임하자
			throw new RuntimeException("Incorrect Json Info. Check Your key and value!");
		}
		
		return object;
	}
	
	/**
	 * Generic Collection Type covert method
	 * @param content
	 * @param clazz
	 * @return T
	 * @throws Exception
	 */
	public static <T> T convertObjectFromJsonStringByTypeRef(String content, TypeReference<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = mapper.readValue(content, clazz);
		} catch (IOException e) {
			// 상위 핸들러에서 에러 위임하자
			throw new RuntimeException("Incorrect Json Info. Check Your key and value!");
		}
		return object;
	}
	
	/**
	 * modelmapper를 사용하기 어려우니 일일이 dto에 대입하자....
	 * @param municipalitySupports
	 * @return List<ResponseDto>
	 */
	public static List<ResponseDto> convertMunicipalitySupportListFromEntity(List<MunicipalitySupport> municipalitySupports) {
		return municipalitySupports.stream().map(mapper -> CommonUtil.convertMunicipalitySupportFromEntity(mapper))
											.collect(Collectors.toList());
	}
	
	/**
	 * modelmapper를 사용하기 어려우니 일일이 dto에 대입하자....
	 * @param municipalitySupport
	 * @return ResponseDto
	 */
	public static ResponseDto convertMunicipalitySupportFromEntity(MunicipalitySupport municipalitySupport) {
		return ResponseDto.builder().municipalityName(municipalitySupport.getMunicipality().getMunicipalityName())
									.supportTarget(municipalitySupport.getSupportTarget())
									.usage(municipalitySupport.getUsage())
									.supportLimit(municipalitySupport.getSupportLimit())
									.rate(municipalitySupport.getRate())
									.recommandInstitute(municipalitySupport.getRecommandInstitute())
									.management(municipalitySupport.getManagement())
									.reception(municipalitySupport.getReception()).build();
	}
	
	/**
	 * param으로 넘어온 length자리만큼 난수를 구한다.
	 * 중복이 나올 수 있다.
	 * @param length
	 * @return String
	 */
	public static String numberGen(int length) {
		String randomString = "";
		for(int i=0; i<length; i++) {
			randomString += new Random().ints(1, 10).findAny().getAsInt();
		}
		return randomString;
	}
	
}
