package com.djenkins.fitness;

import java.sql.Timestamp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.djenkins.fitness.domain.FitnessWeekFilter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	@Bean
	public Docket api() {
		@SuppressWarnings("rawtypes")
		// ignore these classes when generating documentation
		Class[] clazz = { FitnessWeekFilter.class };
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.djenkins.fitness"))
				.paths(PathSelectors.any())
				.build()
				.ignoredParameterTypes(clazz)
				.directModelSubstitute(Timestamp.class, String.class);// substitute timestamp with
																		// string for swagger
																		// documentation
	}
}
