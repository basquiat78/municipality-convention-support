package com.kakaopay.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 
 * queryParam Check and Setting Utility
 * 
 * created by basquiat
 *
 */
@Component
public class PageCheckUtil {

	public static final int DEFAULT_PAGE = 0;
	
	public static final int DEFAULT_SIZE = 10;

	public static final String PAGE_PARAM = "page";
	
	public static final String SIZE_PARAM = "size";
	
	/**
	 * 
	 * 깔끔하진 않지만  ServerRequest로부터 queryParam를 체크한다.
	 * 만일 queryParam을 날리지 않았다면 기본적인 page, size를 체크해서 Pageable로 리턴한다.
	 * 좀더 아름다운 방법을 찾거나 스프링 진영에서 해결해 주거나...
	 * @param request
	 * @return Pageable
	 */
	public static Pageable setUpPageRequest(ServerRequest request) {
		
		int page = DEFAULT_PAGE;
		int size = DEFAULT_SIZE;
		
		if(request.queryParam(PAGE_PARAM).isPresent()) {
			page = request.queryParam(PAGE_PARAM).map(mapper -> Integer.parseInt(mapper)-1).filter(predicate -> predicate > -1).orElse(DEFAULT_PAGE);
		}
		
		if(request.queryParam(SIZE_PARAM).isPresent()) {
			size = request.queryParam(SIZE_PARAM).map(Integer::parseInt).filter(predicate -> predicate > -1).orElse(DEFAULT_SIZE);
		}
		
		return PageRequest.of(page, size);
		
	}
	
}
