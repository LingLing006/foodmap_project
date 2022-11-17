package com.example.foodmap_project.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodmap_project.constants.FoodMapRtnCode;
import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapListResponse;
import com.example.foodmap_project.vo.FoodMapRequest;
import com.example.foodmap_project.vo.FoodMapResponse;

@RestController
public class FoodMapController {

	@Autowired
	private FoodMapService foodMapService;

// 創建一個新的商店
	@PostMapping(value = "/api/createShopInfo")
	public FoodMapResponse createShopInfo(@RequestBody FoodMapRequest req) {

		// 擋掉不符合的資料
		FoodMapResponse res = checkParamForShopCreate(req);

		if (res != null) {
			return res;
		}

		// 創建一個新的商店
		res = foodMapService.createShopInfo(req.getShopName(), req.getCity());

		return res;
	}

// 修改商店資訊
	@PostMapping(value = "/api/updateShopAllInfo")
	public FoodMapResponse updateShopAllInfo(@RequestBody FoodMapRequest req) {

		// 擋掉不符合的資料
		FoodMapResponse res = checkParamForShopUpdate(req);

		if (res != null) {
			return res;
		}

		// 修改商店資訊
		res = foodMapService.updateShopInfo(req.getShopName(), req.getCity(), req.getNewShopName());

		return res;
	}

// 創建一個新的餐點
	@PostMapping(value = "/api/createMealInfo")
	public FoodMapResponse createMealInfo(@RequestBody FoodMapRequest req) {

		// 擋掉不符合的資料
		FoodMapResponse res = checkParamMealForCreate(req);

		if (res != null) {
			return res;
		}

		FoodMapMealId id = new FoodMapMealId(req.getShopName(), req.getMealName());

		res = foodMapService.createMealInfo(id, req.getPrice(), req.getMealLevel());

		return res;
	}

	@PostMapping(value = "/api/updateMealAllInfo")
	public FoodMapResponse updateMealAllInfo(@RequestBody FoodMapRequest req) {

		// 擋掉不符合的資料
		FoodMapResponse res = checkParamMealForUpdate(req);

		if (res != null) {
			return res;
		}

		FoodMapMealId id = new FoodMapMealId(req.getShopName(), req.getMealName());

		res = foodMapService.updateMealInfo(id, req.getPrice(), req.getMealLevel(), req.getNewMealName());

		return res;
	}

	@PostMapping(value = "/api/findShopByCity")
	public FoodMapListResponse findShopByCity(@RequestBody FoodMapRequest req) {

		if (req == null) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}

		// 擋掉不符合的資料
		FoodMapListResponse listRes = checkParamForFindShopByCity(req);

		if (listRes != null) {
			return listRes;
		}

		listRes = foodMapService.findShopByCity(req.getCity(), req.getDisplayAmount());

		return listRes;
	}

	@PostMapping(value = "/api/findShopByShopLevel")
	public FoodMapListResponse findShopByShopLevel(@RequestBody FoodMapRequest req) {

		// 擋掉不符合的資料
		FoodMapListResponse listRes = checkParamForFindShopByShopLevel(req);

		if (listRes != null) {
			return listRes;
		}

		listRes = foodMapService.findShopByShopLevel(req.getShopLevel());

		return listRes;

	}

	@PostMapping(value = "/api/findShopByShopLevelAndMealLevel")
	public FoodMapListResponse findShopByShopLevelAndMealLevel(@RequestBody FoodMapRequest req) {

		FoodMapListResponse listRes = checkParamForFindShopByShopLevelAndMealLevel(req);

		if (listRes != null) {
			return listRes;
		}

		listRes = foodMapService.findShopByShopLevelAndMealLevel(req.getShopLevel(), req.getMealLevel());
		
		return listRes;
	}

//================================================
	private FoodMapResponse checkParamForShopCreate(FoodMapRequest req) {

		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}

		if (!StringUtils.hasText(req.getShopName()) || !StringUtils.hasText(req.getCity())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_CITY_REQUIRED.getMessage());
		}
		return null;
	}

	private FoodMapResponse checkParamForShopUpdate(FoodMapRequest req) {

		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		
		if (!StringUtils.hasText(req.getShopName())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_REQUIRED.getMessage());
		}

		if (!StringUtils.hasText(req.getNewShopName()) && !StringUtils.hasText(req.getCity())) {
			return new FoodMapResponse(FoodMapRtnCode.NEWSHOPNAME_CITY_REQUIRED.getMessage());
		}
		return null;
	}

	private FoodMapResponse checkParamMealForCreate(FoodMapRequest req) {


		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		
		if (req.getMealLevel() <= 0 || req.getMealLevel() > 5 || req.getPrice() <= 0) {
			return new FoodMapResponse(FoodMapRtnCode.LEVEL_PRICE_FAILURE.getMessage());
		}

		if (!StringUtils.hasText(req.getShopName()) || !StringUtils.hasText(req.getMealName())) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_REQUIRED.getMessage());
		}

		return null;
	}

	private FoodMapResponse checkParamMealForUpdate(FoodMapRequest req) {


		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		
		if (!StringUtils.hasText(req.getNewMealName()) && req.getPrice() == 0 && req.getMealLevel() == 0) {
			return new FoodMapResponse("Price = 0 , MealLevel = 0 , NewMealName為空，資訊皆不修改");
		}

		if (!StringUtils.hasText(req.getShopName()) || !StringUtils.hasText(req.getMealName())) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_REQUIRED.getMessage());
		}
		
		if (req.getPrice() < 0) {
			return new FoodMapResponse(FoodMapRtnCode.PRICE_FAIL.getMessage());
		}
		if (req.getMealLevel() < 0 || req.getMealLevel() > 5) {
			return new FoodMapResponse(FoodMapRtnCode.LEVEL_FAILURE.getMessage());
		}
		
		return null;
	}

	private FoodMapListResponse checkParamForFindShopByCity(FoodMapRequest req) {
		
		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		
		if (req.getDisplayAmount() < 0) {
			return new FoodMapListResponse(FoodMapRtnCode.DISPLAYAMOUNT_NEGATIVE.getMessage());
		}
		
		if (!StringUtils.hasText(req.getCity())) {
			return new FoodMapListResponse(FoodMapRtnCode.CITY_REQUIRED.getMessage());
		}
		
		return null;
	}

	private FoodMapListResponse checkParamForFindShopByShopLevel(FoodMapRequest req) {

		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		
		if (req.getShopLevel() < 0 || req.getShopLevel() > 5) {
			return new FoodMapListResponse(FoodMapRtnCode.SHOPLEVEL_FAIL.getMessage());
		}
		return null;
	}

	private FoodMapListResponse checkParamForFindShopByShopLevelAndMealLevel(FoodMapRequest req) {

		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		if (req.getShopLevel() < 0 || req.getShopLevel() > 5) {
			return new FoodMapListResponse(FoodMapRtnCode.SHOPLEVEL_FAIL.getMessage());
		}
		if (req.getMealLevel() < 0 || req.getMealLevel() > 5) {
			return new FoodMapListResponse(FoodMapRtnCode.MEALLEVEL_FAIL.getMessage());
		}
		return null;
	}
}
