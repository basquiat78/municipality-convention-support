package com.kakaopay.municipality.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kakaopay.municipality.model.MunicipalitySupport;
import com.kakaopay.municipality.model.dto.MunicipalityNameDto;
import com.kakaopay.municipality.model.dto.MunicipalityNamesDto;
import com.kakaopay.municipality.model.dto.ResponseDto;
import com.kakaopay.municipality.repository.MunicipalitySupportRepository;
import com.kakaopay.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * MunicipalitySupportService
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("municipalitySupportService")
public class MunicipalitySupportService {
	
	@Autowired
	private MunicipalitySupportRepository municipalitySupportRepository;
	
	/**
	 * 
	 * save All
	 * 
	 * @param municipalitySupports
	 */
	public void saveAll(List<MunicipalitySupport> municipalitySupports) {
		log.info("save all MunicipalitySupport List");
		municipalitySupportRepository.saveAll(municipalitySupports);
	}
	
	/**
	 * show native entity
	 * 정보 확인을 위한 테스트 용도
	 * @return Flux<MunicipalitySupport>
	 */
	public Flux<MunicipalitySupport> findAllWithEntity() {
		return Flux.fromIterable(municipalitySupportRepository.findAll());
	}
	
	/**
	 * 
	 * not paging
	 * 
	 * @return Flux<ResponseDto>
	 */
	public Flux<ResponseDto> findAllWithoutPaging() {
		return Flux.fromIterable(CommonUtil.convertMunicipalitySupportListFromEntity(municipalitySupportRepository.findAll()));
	}
	
	/**
	 * 
	 * with paging
	 * 실제로 WebFlux가 아닌 방식에서는 pageable이 먹히지만 여기선 되지 않아서 실제 다른 방법을 보면 Dto를 통해서 반환하게 되어 있다.
	 * 심플하게 가자...
	 * @param pageable
	 * @return Flux<ResponseDto>
	 */
	public Flux<ResponseDto> findAllWithPaging(Pageable pageable) {
		return Flux.fromIterable(CommonUtil.convertMunicipalitySupportListFromEntity(municipalitySupportRepository.findAll(pageable).stream().collect(Collectors.toList())));
	}
	
	/**
	 * search municipalitySupport with municipalityName
	 * 
	 * like 검색으로
	 * '충청'으로 검색하게 되면 리스트로 나오게 된다.
	 * 따라서 리스트로 받아치자!
	 * @param municipalityName
	 * @return Flux<ResponseDto>
	 */
	public Flux<ResponseDto> findByMunicipalityName(String municipalityName) {
		return Flux.fromIterable(CommonUtil.convertMunicipalitySupportListFromEntity(municipalitySupportRepository.findByMunicipalityMunicipalityNameContaining(municipalityName)));
	}

	/**
	 * update
	 * @param municipalitySupport
	 * @return Mono<ResponseDto>
	 */
	public Mono<ResponseDto> updateMunicipalitySupportById(MunicipalitySupport municipalitySupport) {
		// 그냥 세이브하니....municipality의 정보가 없어서 해당 테이블과의 연관관계가 모호해져 전체 에러가 발생한다...후덜덜...따라서.. municipalityName으로 테이블을 한번 조회를 해야한다.
		// 귀찮...
		MunicipalitySupport selectedMunicipalitySupport = municipalitySupportRepository.findById(municipalitySupport.getId());
		
		municipalitySupport.setMunicipality(selectedMunicipalitySupport.getMunicipality());
		municipalitySupport.setCreatedAt(selectedMunicipalitySupport.getCreatedAt()); // 이거 안하면 null??? 다른 아름다운 방법을 찾기 전까지는...
		System.out.println(municipalitySupport);
		municipalitySupportRepository.save(municipalitySupport);
		log.info("update completed");
		// 업데이트후에는 id로 조회해서 리턴한다.
		return Mono.just(CommonUtil.convertMunicipalitySupportFromEntity(municipalitySupportRepository.findById(municipalitySupport.getId())));
	}
	
	/**
	 * 
	 * 1. 지원한도로 큰넘부터 측 내림차순해서 count만큼의 municipalityName을 찾는다.
	 *    - 추천금액은???? 그냥 제외시킨다. enum에는 0으로 설정했으니 마지막에 붙겠지??
	 * 2. 지원한도가 만일 같다면 이차 보전을 체크하는데 이것은 반대로 작은 넘부터 즉 올림차순의 조건을 갖아야 한다. 복잡하네???
	 * 
	 * @param count
	 * @return Mono<MunicipalityNamesDto>
	 */
	public Mono<MunicipalityNamesDto> findByMunicipalitySupportDesc(int count) {

		List<MunicipalitySupport> municipalitySupports = municipalitySupportRepository.findAll();
		// 이녀석은 dto를 이용해서 해야할듯 싶다.
        // 어짜피 필요한건 municipalityName과 지원한도, 이차보전의 대한 정보만 알면 되며
        // 해당 dto에 정보들을 채워서 list로 반환하고 그녀석은 sort를 이용해 desc하는 방식으로 가자.
        // java8 stream의 막강한 api를 써보자!
        // sorted는 기본적으로 내림차순이기문에 reversed()를 해줘야 한다.
        // 레퍼런스 메소드도 써주자!
		String municipalityNames = municipalitySupports.stream().map(municipalitySupport -> 
																{
																// e.g 50억원 이내, 또는 추천금액 이내...
																String amountString = municipalitySupport.getSupportLimit().split(" ")[0].trim();
															    long amount = CommonUtil.covertCurrencyUnitFromString(amountString);
															    // 2차 보전은 어떻게 하나? ~가 있는 녀석이 있으니 일단 ~로 스플릿하고 사이즈가 1보다 크면 그넘은 average를 구하자
															    // 대출이자 전액이라는 넘이 있는데 이넘은 100%일테니....
															    double averageRate = CommonUtil.calculateRate(municipalitySupport.getRate().trim());
																return MunicipalityNameDto.builder()
																						  // Law Of Demeter 어기는건데????????
																						  // TODO : entitiy내부에서 name으로 바로 반환하게 수정한다.
																						  .municipalityName(municipalitySupport.getMunicipality().getMunicipalityName())
																						  .amount(amount)
																						  .averageRateLimit(averageRate)
																						  .build();
																//amount를 비교해서  reversed하고 동일한 녀석이 있으니 체인메소드로 바로 averageRate를 비교해주자.	
																}).sorted(Comparator.comparing(MunicipalityNameDto::getAmount)
																				    .reversed()
																				    .thenComparing(MunicipalityNameDto::getAverageRateLimit)
																		
																).map(mapper -> mapper.getMunicipalityName()) // string으로 반환해서
																.limit(count)								  // count만 셀렉트하고
																.collect(Collectors.joining(", "));           // join으로 최종적으로 string으로..
		// 보여주기위한 DTO를 만들어야하나???
		return Mono.just(MunicipalityNamesDto.builder().municipalityNames(municipalityNames).build());
	}

}
