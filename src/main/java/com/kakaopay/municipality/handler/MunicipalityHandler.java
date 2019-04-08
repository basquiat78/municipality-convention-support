package com.kakaopay.municipality.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kakaopay.municipality.model.Municipality;
import com.kakaopay.municipality.model.dto.ErrorDescription;
import com.kakaopay.municipality.service.MunicipalityService;

import reactor.core.publisher.Mono;

/**
 * 
 * MunicipalityInformation 정보를 처리하기 위한 핸들러
 * 
 * created by basquiat
 *
 */
@Component
public class MunicipalityHandler {

	@Autowired
	private  MunicipalityService municipalityInfoService;

	/**
	 * 
	 * 업로드한 파일을 처리한다.
	 * 
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> upload(ServerRequest request) {
		/*
		 * 1. 받은 파일을 csv처리기로 객체로 변환해서 database에 입력한다.
		 * 2. 파일 업로드 오류등 오류가 발생시에 에러처리
		 *    - ErrorDescription에 정보를 담아서 보낸다.
		 */
		return request.body(BodyExtractors.toMultipartData()).flatMap(multiValueMap -> {
																				Mono<String> mono = municipalityInfoService.readCsvFileAndSave(multiValueMap);
																				return ServerResponse.ok().contentType(APPLICATION_JSON).body(mono, String.class);
																			 })
															 .onErrorResume(throwable ->
															 							Mono.just(ErrorDescription.builder()
															 													  .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
															 													  .message("File Upload Failed. Try Again!").build())
															 								.flatMap(errorDescription -> ServerResponse.ok().syncBody(errorDescription)));
    }
	
	/**
	 * MunicipalityInformation 가져오기
	 * @param request
	 * @return Mono<ServerResponse>
	 */
	public Mono<ServerResponse> getMunicipalityoList(ServerRequest request) {
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(municipalityInfoService.findAll(), Municipality.class);
	}
	
//
//	public Mono<ServerResponse> getEcoInfoByRegion(ServerRequest request) {
//		Mono<EcologicalInformation> mono = request.bodyToMono(EcologicalInformation.class).flatMap(ecoInfo -> ecoInfoService.getEcoInfoByResion(ecoInfo.getRegion())).onErrorResume(throwable -> Mono.just(new EcologicalInformation()));
//		return ServerResponse.ok().contentType(APPLICATION_JSON).body(mono, EcologicalInformation.class);
//	}
	
}
