package com.kakaopay.municipality.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kakaopay.municipality.model.MunicipalitySupport;
import com.kakaopay.municipality.model.dto.MunicipalityNamesDto;
import com.kakaopay.municipality.model.dto.RecommandInstitutesDto;
import com.kakaopay.municipality.model.dto.ResponseDto;
import com.kakaopay.municipality.service.MunicipalitySupportService;
import com.kakaopay.util.PageCheckUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * MunicipalitySupport 정보를 처리하기 위한 핸들러
 * 
 * created by basquiat
 *
 */
@Component
public class MunicipalitySupportHandler {

	@Autowired
	private  MunicipalitySupportService municipalitySupportService;
	
	/**
	 * paging처리 없이 MunicipalitySupport를 Entity형식으로 전부 가져오기
	 * 업데이트, 같은 경웨 생성일, 수정일을 체크하기 위한 체크용
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> getMunicipalitySupportEntityList(ServerRequest request) {
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(municipalitySupportService.findAllWithEntity(), MunicipalitySupport.class);
	}
	
	/**
	 * paging처리 없이 MunicipalitySupport 가져오기
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> getMunicipalitySupportList(ServerRequest request) {
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(municipalitySupportService.findAllWithoutPaging(), ResponseDto.class);
	}
	
	/**
	 * paging처리하자.
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> getMunicipalitySupportListWithPaging(ServerRequest request) {
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(municipalitySupportService.findAllWithPaging(PageCheckUtil.setUpPageRequest(request)), ResponseDto.class);
	}
	
	/**
	 * find by MunicipalityName
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> getByMunicipalityName(ServerRequest request) {
		Flux<ResponseDto> flux = request.bodyToFlux(MunicipalitySupport.class)
										.flatMap(municipalitySupport -> municipalitySupportService.findByMunicipalityName(municipalitySupport.getMunicipalityName()));
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(flux, ResponseDto.class);
	}
	
	/**
	 * update MunicipalitySupport
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> updateMunicipalitySupport(ServerRequest request) {
		Mono<ResponseDto> mono = request.bodyToMono(MunicipalitySupport.class)
									    .flatMap(municipalitySupport -> municipalitySupportService.updateMunicipalitySupportById(municipalitySupport));
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(mono, ResponseDto.class);
	}
	
	/**
	 * 지원 한도, 이차 보전율 조건 검색
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> getByMunicipalitySupportDesc(ServerRequest request) {
		Mono<MunicipalityNamesDto> mono = municipalitySupportService.findByMunicipalitySupportDesc(Integer.valueOf(request.pathVariable("count")));
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(mono, MunicipalityNamesDto.class);
	}
	
	/**
	 *  이차 보전율이 가장 낮은 추천기관 검색
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> getByMinRateRecommandInstitute(ServerRequest request) {
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(municipalitySupportService.findByMinRateRecommandInstitutes(), RecommandInstitutesDto.class);
	}
	
	
}
