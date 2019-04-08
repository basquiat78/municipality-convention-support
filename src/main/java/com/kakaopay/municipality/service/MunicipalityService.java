package com.kakaopay.municipality.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.kakaopay.municipality.model.Municipality;
import com.kakaopay.municipality.model.MunicipalitySupport;
import com.kakaopay.municipality.model.dto.CsvDto;
import com.kakaopay.municipality.repository.MunicipalityRepository;
import com.kakaopay.util.CommonUtil;
import com.kakaopay.util.CsvUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * Municipality Service
 * 
 * created by basquiat
 *
 */
@Slf4j
@Service("municipalityService")
public class MunicipalityService {
	
	@Value("${files.upload.dir}")
	private String FILE_DIR;
	
	@Autowired
	private MunicipalitySupportService municipalitySupportService;
	
	@Autowired
	private MunicipalityRepository municipalityRepository;
	
	/**
	 * csv file upload
	 * @param map
	 * @return Mono<String>
	 */
	@Transactional
	public Mono<String> readCsvFileAndSave(MultiValueMap<String, Part> map) {
		log.info("readCsvFileAndSave");
		FilePart filePart = (FilePart) map.toSingleValueMap().get("file");
		filePart.transferTo( new File(FILE_DIR.concat(filePart.filename())) );

		// parse csvFile
		List<CsvDto> csvDtoList = CsvUtil.csvFileParse( new File(FILE_DIR.concat(filePart.filename())), CsvDto.class);

		// 1. 코드는 일단 단순하게 1부터 딴다.
		// 2. 코드 패턴을 preFix : region + index + 3자리 난수로 정한다.
		AtomicInteger index = new AtomicInteger();
//		List<Municipality> municipalitys = csvDtoList.stream().map(mapper -> 
//																			{
//																			// 3자리 난수와 inde를 결합해서 코드 생성한다.
//																			String suffixCode = CommonUtil.numberGen(3);
//																			String municipalityCode = "region"+index.incrementAndGet()+suffixCode;
//																					
//																			Municipality municipality = Municipality.builder()
//																					.municipalityName(mapper.getMunicipalityName())
//																					.municipalityCode(municipalityCode)
//																					.build();
//																			
//																			 return municipality;
//																			}
//																	   ).collect(Collectors.toList());
		List<MunicipalitySupport> municipalitySupports = csvDtoList.stream().map(mapper -> 
																					{
																						
																						// 3자리 난수와 inde를 결합해서 코드 생성한다.
																						String suffixCode = CommonUtil.numberGen(3);
																						String municipalityCode = "region"+index.incrementAndGet()+suffixCode;
																						
																						Municipality municipality = Municipality.builder()
																																.municipalityName(mapper.getMunicipalityName())
																																.municipalityCode(municipalityCode)
																																.build();
																						
																						return MunicipalitySupport.builder().municipalityCode(municipalityCode)
																															.municipality(municipality)
																															.supportTarget(mapper.getSupportTarget())
																															.usage(mapper.getUsage())
																															.supportLimit(mapper.getSupportLimit())
																															.rate(mapper.getRate())
																															.recommandInstitute(mapper.getRecommandInstitute())
																															.management(mapper.getManagement())
																															.reception(mapper.getReception())
																															.build();
																					
																					}).collect(Collectors.toList());

		municipalitySupportService.saveAll(municipalitySupports);
		log.info("complete read Csv File And Save!");
		return Mono.just("File Upload Completed");
	}
	
	/**
	 * find All
	 * 
	 * 데이터 확인을 위한...
	 * 
	 * @return Flux<Municipality>
	 */
	public Flux<Municipality> findAll() {
		return Flux.fromIterable(municipalityRepository.findAll());
	}

}
