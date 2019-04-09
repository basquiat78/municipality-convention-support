package com.kakaopay.route;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kakaopay.municipality.handler.MunicipalityHandler;
import com.kakaopay.municipality.handler.MunicipalitySupportHandler;
import com.kakaopay.municipality.handler.ViewHandler;

/**
 * 
 * route 관련 bean은 api부분과 view을 나눠서 작성한다.
 * 
 * created by basquiat
 *
 */
@Configuration
public class RouteConfigration implements WebFluxConfigurer {

	/**
	 * municipalitySupport을 위한 route
	 * @param municipalitySupportHandler
	 * @return RouterFunction<ServerResponse>
	 */
    @Bean
    public RouterFunction<ServerResponse> municipalitySupportRoutes(MunicipalitySupportHandler municipalitySupportHandler) {
        return RouterFunctions.route(GET("/api/v1/municipalitys/support"), municipalitySupportHandler::getMunicipalitySupportList)
        					  .andRoute(GET("/api/v1/municipalitys/support/entity"), municipalitySupportHandler::getMunicipalitySupportEntityList)
		        			  .andRoute(GET("/api/v1/municipalitys/support/withpage"), municipalitySupportHandler::getMunicipalitySupportListWithPaging)
		        			  .andRoute(GET("/api/v1/municipalitys/support/name"), municipalitySupportHandler::getByMunicipalityName)
		        			  .andRoute(PATCH("/api/v1/municipalitys/support"), municipalitySupportHandler::updateMunicipalitySupport)
		        			  .andRoute(GET("/api/v1/municipalitys/support/limits/{count}"), municipalitySupportHandler::getByMunicipalitySupportDesc)
		        			  .andRoute(GET("/api/v1/municipalitys/support/rate/min"), municipalitySupportHandler::getByMinRateRecommandInstitute);
    }
	
    /**
	 * Municipipality을 위한 route
	 * @param municipalityHandler
	 * @return RouterFunction<ServerResponse>
	 */
    @Bean
    public RouterFunction<ServerResponse> municipalityRoutes(MunicipalityHandler municipalityHandler) {
        return RouterFunctions.route(POST("/api/v1/upload"), municipalityHandler::upload)
        					  .andRoute(GET("/api/v1/municipalityinfos"), municipalityHandler::getMunicipalityoList); // <-- test용
    }
    
	/**
	 * view단을 위한 route
	 * @param viewHandler
	 * @return RouterFunction<ServerResponse>
	 */
    @Bean
    public RouterFunction<ServerResponse> viewRoutes(ViewHandler viewHandler) {
        return RouterFunctions.route(GET("/view/upload"), viewHandler::uploadView);
    }

}