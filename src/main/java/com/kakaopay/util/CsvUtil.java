package com.kakaopay.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import com.kakaopay.municipality.model.dto.CsvDto;
import com.opencsv.CSVReader;

/**
 * 
 * CSV Utility
 * 
 * created by basquiat
 *
 */
public class CsvUtil {

	/**
	 * csv file to List<CsvDto> mapping
	 * @param csvFile
	 * @param clazz
	 * @return List<T>
	 */
	@SuppressWarnings({"resource", "unchecked"})
	public static <T> List<T> csvFileParse(File csvFile, Class<T> clazz) {

		List<T> list = null;
		
		try {
			// UTF-8은 한글이 깨짐, EUC-KR로 받아친다.
			// 시스템마다 다를지도 모르는데 이걸 이렇게 처리해야 맞는걸까????
			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csvFile), "EUC-KR"));
			reader.skip(1);
			list = (List<T>) reader.readAll().stream().map(mapper -> CsvDto.builder().id(Long.parseLong(mapper[0]))
																					 .municipalityName(mapper[1])
																					 .supportTarget(mapper[2])
																					 .usage(mapper[3])
																					 .supportLimit(mapper[4])
																					 .rate(mapper[5])
																					 .recommandInstitute(mapper[6])
																					 .management(mapper[7])
																					 .reception(mapper[8])
																					 .build()
		  									     		   ).collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
		
    }

}
