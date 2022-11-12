package com.example.foodmap_project.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodmap_project.constants.FoodMapRtnCode;
import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.entity.FoodMapShop;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapListResponse;
import com.example.foodmap_project.vo.FoodMapRequest;
import com.example.foodmap_project.vo.FoodMapRequest2;
import com.example.foodmap_project.vo.FoodMapResponse;

@RestController
public class FoodMapController {

	@Autowired
	private FoodMapService foodMapService;

	@PostMapping(value = "/api/createShopInfo")
	public FoodMapResponse createShopInfo(@RequestBody FoodMapRequest req) {

		if (req == null) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}

		FoodMapResponse res = checkParamShop(req);// 擋掉不符合的資料
		if (res != null) {
			return res;
		}

		res = foodMapService.createShopInfo(req.getShopName(), req.getCity());
		return res;
	}

	@PostMapping(value = "/api/createMealInfo")
	public FoodMapResponse createMealInfo(@RequestBody FoodMapRequest req) {
		if (req == null) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		if (req.getMealLevel() == 0 || req.getPrice() == 0) {
			return new FoodMapResponse(FoodMapRtnCode.LEVEL_PRICE_FAILURE.getMessage());
		}
		FoodMapResponse res = checkParamMeal(req);
		if (res != null) {
			return res;
		}
		FoodMapMealId id = new FoodMapMealId(req.getShopName(), req.getMealName());
		res = foodMapService.createMealInfo(id, req.getPrice(), req.getMealLevel());
		return res;

	}

	@PostMapping(value = "/api/updateShopAllInfo")
	public FoodMapResponse updateShopAllInfo(@RequestBody FoodMapRequest req) {
		if (req == null) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getShopName())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getNewShopName()) && !StringUtils.hasText(req.getCity())) {
			return new FoodMapResponse(FoodMapRtnCode.NEWSHOPNAME_CITY_REQUIRED.getMessage());
		}

		FoodMapResponse res = foodMapService.updateShopInfo(req.getShopName(), req.getCity(), req.getNewShopName());
		return res;
	}

//===updateMealAllInfo=============================================================================	
	@PostMapping(value = "/api/updateMealAllInfo")
	public FoodMapResponse updateMealAllInfo(@RequestBody FoodMapRequest req) {
		if (req == null) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getNewMealName()) && req.getPrice() == 0 && req.getMealLevel() == 0) {
			return new FoodMapResponse("Price=0,MealLevel=0,NewMealName為空，資訊皆不修改");
		}

		FoodMapResponse res = checkParamMeal(req);
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
		if (req.getDisplayAmount() < 0) {
			return new FoodMapListResponse(FoodMapRtnCode.DISPLAYAMOUNT_NEGATIVE.getMessage());
		}
		if (!StringUtils.hasText(req.getCity())) {
			return new FoodMapListResponse(FoodMapRtnCode.CITY_REQUIRED.getMessage());
		}

		FoodMapListResponse listRes = foodMapService.findShopByCity(req.getCity(), req.getDisplayAmount());
		return listRes;
	}

	@PostMapping(value = "/api/findShopByShopLevel")
	public FoodMapListResponse findShopByShopLevel(@RequestBody FoodMapRequest req) {
		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		if (req.getShopLevel() < 0 || req.getShopLevel() > 5) {
			return new FoodMapListResponse(FoodMapRtnCode.SHOPLEVEL_FAIL.getMessage());
		}

		FoodMapListResponse listRes = foodMapService.findShopByShopLevel(req.getShopLevel());

		return listRes;

	}

	@PostMapping(value = "/api/findShopByShopLevelAndMealLevel")
	public FoodMapListResponse findShopByShopLevelAndMealLevel(@RequestBody FoodMapRequest req) {
		if (Objects.isNull(req)) {
			new FoodMapResponse(FoodMapRtnCode.REQ_REQUIRED.getMessage());
		}
		if (req.getShopLevel() < 0 || req.getShopLevel() > 5) {
			return new FoodMapListResponse(FoodMapRtnCode.SHOPLEVEL_FAIL.getMessage());
		}
		if (req.getMealLevel() < 0 || req.getMealLevel() > 5) {
			return new FoodMapListResponse(FoodMapRtnCode.MEALLEVEL_FAIL.getMessage());
		}
		FoodMapListResponse listRes = foodMapService.findShopByShopLevelAndMealLevel(req.getShopLevel(),
				req.getMealLevel());
		return listRes;

	}

//================================================
	private FoodMapResponse checkParamShop(FoodMapRequest req) {
		if (!StringUtils.hasText(req.getShopName()) || !StringUtils.hasText(req.getCity())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_CITY_REQUIRED.getMessage());
		}
		return null;
	}

	private FoodMapResponse checkParamMeal(FoodMapRequest req) {
		if (!StringUtils.hasText(req.getShopName()) || !StringUtils.hasText(req.getMealName())) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_REQUIRED.getMessage());
		} else if (req.getPrice() < 0) {
			return new FoodMapResponse(FoodMapRtnCode.PRICE_FAIL.getMessage());
		} else if (req.getMealLevel() < 0 || req.getMealLevel() > 5) {
			return new FoodMapResponse(FoodMapRtnCode.LEVEL_FAILURE.getMessage());
		}
		return null;
	}
}
