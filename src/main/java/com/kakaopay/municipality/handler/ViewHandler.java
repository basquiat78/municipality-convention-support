package com.kakaopay.municipality.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * 
 * View Handler
 * 
 * 업로드할 화면을 보여주기 위한 Handler
 * 
 * 화면은 간략하게 보여주기 위해서 스프링부트에서 가장 무난하게 사용하고 사용해 봤던 FreeMarker를 사용했다. 
 * 
 * created by basquiat
 *
 */
@Component
public class ViewHandler {

	/**
	 * 
	 * csv파일을 업로드 할 화면을 렌더링한다.
	 * 
	 * @param request
	 * @return Mono<ServerResponse>
	 */
    public Mono<ServerResponse> uploadView(ServerRequest request) {
        return ServerResponse.ok().render("uploadView");
    }
    
}
