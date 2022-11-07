package com.example.foodmap_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration //��spring�Ӹ��J�����O�]�w
@EnableSwagger2 //�ҥ� swagger2.createRestApi ��ƫإ� Docket �� Bean
public class SwaggerConfig {

		//�إ�api���򥻸�T(�o�ǰ򥻸�T�|��ܦb��󭶭�)
		//�y�X���}:http://�M�׹��IP:port/swagger-ui.html
		public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
			.title("RESTful APIs")
			.build();
		
		@Bean
		public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(DEFAULT_API_INFO)//���API���򥻸�T�A�i���[
					.select()//�^�Ǥ@��ApiSelectorBuilder ��� �A�Ψӱ���Ǥ����i�H��Swagger�Ӯi�{
					//�]�w�M�󱽴y���|
					//Swagger�|���y�M��U�Ҧ�Controller �w�q��API1�ò��ͤ��
					//
					.apis(RequestHandlerSelectors.basePackage("com.example.foodmap_project.controller"))
					.paths(PathSelectors.any())
					.build();
		}
	}

