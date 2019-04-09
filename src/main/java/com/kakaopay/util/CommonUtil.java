package com.kakaopay.util;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.municipality.model.MunicipalitySupport;
import com.kakaopay.municipality.model.dto.ResponseDto;
import com.kakaopay.municipality.type.CurrencyType;

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
	 * 
	 * 생각해 보니 업데이트 삭제는 id로 지우는게 짱이지..
	 * 
	 * 과제에서는 보이지 않지만 crud를 위해서 id까지 json response에 담자...
	 * 
	 * @param municipalitySupport
	 * @return ResponseDto
	 */
	public static ResponseDto convertMunicipalitySupportFromEntity(MunicipalitySupport municipalitySupport) {
		return ResponseDto.builder().id(municipalitySupport.getId())
									.municipalityName(municipalitySupport.getMunicipality().getMunicipalityName())
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
		return IntStream.range(0, length).mapToObj(mapper -> new Random().ints(1, 10).findAny().getAsInt()+"").collect(Collectors.joining(""));
	}
	
	/**
	 * entity의 정보를 통해서 액수로 반환하자...
	 * enum을 사용하자....
	 * @param unit
	 * @param amount
	 * @return long
	 */
	public static long covertCurrencyUnitFromString(String amountString) {

		if(amountString.contains("추천금액")) {
			return CurrencyType.valueOf(amountString).calculate(0);
		}
		String unit = amountString.replaceAll("[^ㄱ-힣]", ""); //<-- 뒤에 한글만
		if(unit.contains("이내")) { // 어라...'50억이내'...이런 넘이 딱 하나 있네...이것떄문에 if써야하니??
			unit = unit.replace("이내", "");
		}
		
		int value = Integer.valueOf(amountString.replaceAll("[^0-9]", "")); //<-- 뒤에 숫자만
		return CurrencyType.valueOf(unit).calculate(value);
	}
	
	/**
	 * 이차보전에 대한 값을 구한다.
	 * 단일일수도 있고 아닐수도 있고...전액일수도 있고...
	 * @param rateString
	 * @return double
	 */
	public static double calculateRate(String rate) {
		// 1. 대출이자 전액인 넘을 먼저 처리하자.
		if(rate.contains("전액")) {
			return Double.valueOf(100);
		}
		
		String[] rateArray = rate.split("~");
		
		double averageRate;
		
		// 1보다 크다면 2개가 존재
		if(rateArray.length > 1) {
			double min = Double.valueOf(rateArray[0].replace("%", "").trim());
			double max = Double.valueOf(rateArray[1].replace("%", "").trim());
			averageRate = (min+max)/2; 
		} else {
			averageRate = Double.valueOf(rateArray[0].replace("%", "").trim());
		}
		return averageRate;
	}
	
//	public static void main(String[] args) {
//		
//		String a = "추천금액";
//		System.out.println(a.contains("추천금액"));
//		
//		String rateString1 = "3%~3.6%";
//		String rateString2 = "10%~12%";
//		String rateString3 = "10%~15%";
//		String rateString4 = "10%~16%";
//		String rateString5 = "6.8%";
//		String rateString6 = "대출이자 전액";
//
//		System.out.println(CommonUtil.calculateRate(rateString1));
//		System.out.println(CommonUtil.calculateRate(rateString2));
//		System.out.println(CommonUtil.calculateRate(rateString3));
//		System.out.println(CommonUtil.calculateRate(rateString4));
//		System.out.println(CommonUtil.calculateRate(rateString5));
//		System.out.println(CommonUtil.calculateRate(rateString6));
//	
//		String amountString = "10억원";
//		System.out.println(amountString.replaceAll("[^ㄱ-힣]", "")); // <-- 뒤에 한글만
//		System.out.println(amountString.replaceAll("[^0-9]", "")); // <-- 숫자만
//		
//	}
//	
}
