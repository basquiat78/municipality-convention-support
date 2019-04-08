package com.kakaopay.municipality.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kakaopay.municipality.model.MunicipalitySupport;
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
		Flux<ResponseDto> flux = municipalitySupportService.findAllWithPaging(PageCheckUtil.setUpPageRequest(request));
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(flux, ResponseDto.class);
	}
	
	/**
	 * find by MunicipalityName
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> findByMunicipalityName(ServerRequest request) {
		Flux<ResponseDto> flux = request.bodyToFlux(MunicipalitySupport.class)
												.flatMap(municipalitySupport -> municipalitySupportService.findByMunicipalityName(municipalitySupport.getMunicipalityName()));
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(flux, ResponseDto.class);
	}
	
}
