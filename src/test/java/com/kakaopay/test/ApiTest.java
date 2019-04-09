package com.kakaopay.test;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.kakaopay.municipality.model.MunicipalitySupport;
import com.kakaopay.municipality.model.dto.ResponseDto;
import com.kakaopay.municipality.service.MunicipalitySupportService;
import com.kakaopay.util.PageCheckUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * 
 * 1. testfiles의 파일을 읽어서 업로드하고 db에 인서트한다.
 *    - file upload라 컨트롤러 테스트 진행
 *	
 * 2. paging처리한 리스트를 가져온다.
 * 
 * 3. municipalityName으로 검색한 정보를 가져온다.
 * 
 * 4. 특정 아이디 정보를 업데이트하고 업데이트된 정보를 가져온다.
 * 
 * 5. 출력 개수를 보내면 조건에 맞는 municipalityNames를 가져온다.
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class ApiTest {

	@Autowired
    private WebTestClient webTestClient;
	
	@Autowired
	private MunicipalitySupportService municipalitySupportService;
	
	private int page;
	
	private int size;
	
	private String municipalityName;
	
	@Before
    public void init() {
		page = PageCheckUtil.DEFAULT_PAGE;
		size = PageCheckUtil.DEFAULT_SIZE;
		municipalityName = "충청";
    }
	
	/**
	 * 1. file upload controller test
	 */
	@Test
	public void apiTest1() {
		
		/**
		 * MultipartBodyBuilder를 이용해서 testfiles 폴더에 있는 csv를 읽어온다.
		 * WebTestClient를 통해서 업로드하는 api를 호출해서 파일을 읽고 디비에 인서트하는 과정을 거친다.
		 * 
		 * 이후 테스트는 transaction으로 묶어서 순차적으로 하나씩 진행하고 롤백할것이다.
		 * 
		 */
		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("file", new FileSystemResource("testfiles/서버개발_사전과제1_지자체협약지원정보_16년12월현재__최종.csv"));
		
		ResponseSpec responseSpec = webTestClient.post()
												 .uri("/api/v1/upload")
												 .contentType(MediaType.MULTIPART_FORM_DATA)
												 .syncBody(builder.build())
												 .exchange()
												 .expectStatus().isOk();
		
		responseSpec.expectBody(String.class).isEqualTo("File Upload Completed");
	}
	
	
	/**
	 * page처리해서 MunicipalitySupport리스트를 가져온다.
	 */
	@Test
	public void apiTest2() {
		final List<ResponseDto> responseDtos = new ArrayList<>();

		/**
		 * 1. flux 정보는  아이디가 8보다 큰 MunicipalitySupport로 필터링한다. e.g. 2개가 나오게 된다.
		 * 2. subscribe에서 이 2개를 municipalitySupports에 담는다.
		 * 
		 * 3. StepVerifier에서 해당 flux를 검증한다.
		 *    - 최초의 expectNext에서 list의 첫번째는 id가 9인 MnicipalitySupport가 담겨져 있고 flux 역시 그렇기 때문에 첫번째 next는 통과
		 *    - 그리고 나머지 한개가 남았으니 expectNextCount는 1개가 될것이다.
		 *  
		 */
		Flux<ResponseDto> flux = municipalitySupportService.findAllWithPaging(PageRequest.of(page, size))
													       .filter(responseDto -> responseDto.getId() > 8);
		flux.subscribe(responseDtos::add);
		
		StepVerifier.create(flux)
					.expectNext(responseDtos.get(0))
					.expectNextCount(1)
					.verifyComplete();
	}
	
	/**
	 * municipalityName으로 조회한 정보를 가져온다.
	 * like 검색으로 리스트로 나올 수 있다.
	 * 
	 * controller테스트를 하면 참 좋겠지만 get방식의 경우 json정보를 보내줄 방법이 없다. 
	 * 
	 * 현재 webflux의 WebTestClient의 get으로는 보낼 방법이 없다.
	 * 
	 * restful api url를 {}로 pathVariable로 받아치면 좋겠지만 과제의 제약사항인 모든 입출력은 json에 최대한 맞추기 위해서 이 부분은
	 * 
	 * service부분만 테스트한다.
	 * 
	 * 관련 테스트는 postman같은 것을 이용해서 Body의 raw로 JSON (applicaton/json)으로 선택해서 json형식으로 테스트할 수 있다.
	 * 
	 */
	@Test
	public void apiTest3() {
		
		final List<ResponseDto> responseDtos = new ArrayList<>();
		
		// 강원도 또는 강원으로 검색시에는 하나가 나온다..물론 '충청'으로 검색하게 되면 데이터 파일을 보면 두개가 나오게 된다.
		// 여기선 충청으로 검색해서 한다.
		Flux<ResponseDto> flux = municipalitySupportService.findByMunicipalityName(municipalityName);
		flux.subscribe(responseDtos::add);
		
		/**
		 * index 0, 1만 next로 assert하고 nextCount하면서 Complete시킨다.
		 */
		StepVerifier.create(flux)
					.expectNext(responseDtos.get(0))
					.expectNext(responseDtos.get(1))
					.expectNextCount(0)
					//.expectNextCount(1) 에러가 난다. 당연한 결과 2개가 리턴되기 때문에 nextCount는 0된다.
					.verifyComplete();
	}
	
	/**
	 * update
	 */
	@Test
	public void apiTest4() {
		
		// update할 정보를 만들어 놓는다.
		// usage를 운전이 아닌 우운전으로 수정해보자
		MunicipalitySupport municipalitySupport = MunicipalitySupport.builder().id(2)
																			   .municipalityName("자연과 문화를 함께 즐기는 설악산 기행")
																			   .supportTarget("문화생태체험,자연생태체험")
																			   .usage("우운전")
																			   .supportLimit("8억원 이내")
																			   .rate("3%~5%")
																			   .recommandInstitute("강원도")
																			   .management("춘천지점")
																			   .reception("강원도 소재 영업점").build();
		
		// 실제로 update요청을 하게 되면 업데이트한 이후에 업데이트한 내용을 다시 select해서 보여주게 된다.
		// 따라서 jsonPath의 usage는 '우운전'관 동일해야 한다
		webTestClient.patch()
					 .uri("/api/v1/municipalitys/support")
					 .contentType(MediaType.APPLICATION_JSON)
					 .accept(MediaType.APPLICATION_JSON)
					 .body(Mono.just(municipalitySupport), MunicipalitySupport.class)
					 .exchange()
					 .expectStatus().isOk()
					 .expectHeader().contentType(MediaType.APPLICATION_JSON)
					 .expectBody()
					 //.jsonPath("$.usage").isEqualTo("우운전111")
					 .jsonPath("$.usage").isEqualTo("우운전");
	}
	
	/**
	 * delete관련 요청은 없어서 만들지 않았다.
	 * 마지막 지원 한도, 이차보전을 통한 municipalityNames를 보여주기 위한 테스트
	 * 
	 * 과제의 제약조건에 의하면 모든 입출력은 json이지만 이 부분만은 pathVariable를 이용한다.
	 * 
	 */
	@Test
	public void apiTest5() {

		// 조건 입출력 개수를 2개로 제한해 보자
		// 실제 기대되는 값은 municipalityNames: "경기도, 제주도"이다.
		webTestClient.get()
					 .uri("/api/v1/municipalitys/limits/{count}", 2)
					 .exchange()
					 .expectStatus().isOk()
					 .expectHeader().contentType(MediaType.APPLICATION_JSON)
					 .expectBody()
					 //.jsonPath("$.municipalityNames").isEqualTo("경기도, 제주도1111") java.lang.AssertionError: JSON path "$.municipalityNames" expected:<경기도, 제주도111> but was:<경기도, 제주도>
					 .jsonPath("$.municipalityNames").isEqualTo("경기도, 제주도");
		
	}
	
}