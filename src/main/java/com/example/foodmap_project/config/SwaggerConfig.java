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


@Configuration //讓spring來載入該類別設定
@EnableSwagger2 //啟用 swagger2.createRestApi 函數建立 Docket 的 Bean
public class SwaggerConfig {

		//建立api的基本資訊(這些基本資訊會顯示在文件頁面)
		//造訪網址:http://專案實際IP:port/swagger-ui.html
		public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
			.title("RESTful APIs")
			.build();
		
		@Bean
		public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(DEFAULT_API_INFO)//顯示API的基本資訊，可不加
					.select()//回傳一個ApiSelectorBuilder 實例 ，用來控制那些介面可以給Swagger來展現
					//設定套件掃描路徑
					//Swagger會掃描套件下所有Controller 定義的API1並產生文件
					//
					.apis(RequestHandlerSelectors.basePackage("com.example.foodmap_project.controller"))
					.paths(PathSelectors.any())
					.build();
		}
	}

