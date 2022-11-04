package com.example.foodmap_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodmap_project.constants.FoodMapRtnCode;
import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Meal_Id;
import com.example.foodmap_project.entity.FoodMap_Shop;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapRequest;
import com.example.foodmap_project.vo.FoodMapResponse;

@RestController
public class FoodMapController {

	@Autowired
	private FoodMapService foodMapService;

	@PostMapping(value = "/api/createShopInfo")
	public FoodMapResponse createShopInfo(@RequestBody FoodMapRequest req) {
		FoodMapResponse res = checkParamShop(req);
		if (res != null) {
			return res;
		}

		FoodMap_Shop shop = foodMapService.createShopInfo(req.getShopName(), req.getCity());
		if (shop == null) {
			return new FoodMapResponse(shop, FoodMapRtnCode.SHOPNAME_EXISTED.getMessage());
		}
		return new FoodMapResponse(shop, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/createMealInfo")
	public FoodMapResponse createMealInfo(@RequestBody FoodMapRequest req) {
		FoodMapResponse res1 = checkParamMeal(req);
		if (res1 != null) {
			return res1;
		}

		FoodMap_Meal_Id id = new FoodMap_Meal_Id(req.getShopName(), req.getMealName());
		FoodMap_Meal meal = foodMapService.createMealInfo(id, req.getPrice(), req.getMealLevel());
		if (meal == null) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_EXISTED.getMessage());
		}

		FoodMapResponse res = new FoodMapResponse();
		res.setMeal(meal);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;

	}

	@PostMapping(value = "/api/updateShopAllInfo")
	public FoodMapResponse updateShopAllInfo(@RequestBody FoodMapRequest req) {
		if (!StringUtils.hasText(req.getShopName())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_REQUIRED.getMessage());
		}

		FoodMap_Shop shop = foodMapService.findShopById(req.getShopName());
		if (shop != null) {
			if (StringUtils.hasText(req.getCity())) {
				shop = foodMapService.updateShopInfo(req.getShopName(), req.getCity());// 先判斷有沒有city，有的話先改
			}
			if (StringUtils.hasText(req.getNewShopName())) {
				shop = foodMapService.updateShopName(req.getShopName(), req.getNewShopName());// 改完city之後再改new_shop_name
			}
			return new FoodMapResponse(shop, FoodMapRtnCode.SUCCESSFUL.getMessage());
		}
		return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_INEXISTED.getMessage());
	}

	@PostMapping(value = "/api/updateMealAllInfo")
	public FoodMapResponse updateMealAllInfo(@RequestBody FoodMapRequest req) {
//		if (!StringUtils.hasText(req.getShopName()) || !StringUtils.hasText(req.getMealName())) {
//			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_REQUIRED.getMessage());
//		}

		FoodMapResponse res1 = checkParamMeal(req);
		if (res1 != null) {
			return res1;
		}
		
		FoodMap_Meal_Id id = new FoodMap_Meal_Id(req.getShopName(), req.getMealName());
		FoodMap_Meal meal = foodMapService.findMealById(id);

		if (meal != null) {
			if (req.getPrice() != 0 || req.getMealLevel() != 0) {
				int a = req.getMealLevel();
				meal = foodMapService.updateMealInfo(id, req.getPrice(), req.getMealLevel());
			}
			FoodMap_Meal meal1 = checkNewShopAndMealName(req, meal);
			if (meal1 != null) {
				return new FoodMapResponse(meal1, FoodMapRtnCode.SUCCESSFUL.getMessage());
			}
			return new FoodMapResponse(meal, FoodMapRtnCode.SUCCESSFUL.getMessage());
		}

		return new FoodMapResponse(FoodMapRtnCode.MEALNAME_INEXISTED.getMessage());
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
			return new FoodMapResponse(FoodMapRtnCode.PRICE_NEGATIVE.getMessage());
		} else if (req.getMealLevel() < 1 || req.getMealLevel() > 5) {
			return new FoodMapResponse(FoodMapRtnCode.LEVEL_FAILURE.getMessage());
		}
		return null;
	}

	private FoodMap_Meal checkNewShopAndMealName(FoodMapRequest req, FoodMap_Meal meal) {

		FoodMap_Meal_Id id = new FoodMap_Meal_Id(req.getShopName(), req.getMealName());
//		FoodMap_Meal meal = foodMapService.findMealById(id);

		if (StringUtils.hasText(req.getNewShopName()) && StringUtils.hasText(req.getNewMealName())) {
			FoodMap_Meal_Id newId = new FoodMap_Meal_Id(req.getNewShopName(), req.getNewMealName());
			meal = foodMapService.updateMealId(id, newId);
			return meal;
		}
		if (StringUtils.hasText(req.getNewShopName()) && !StringUtils.hasText(req.getNewMealName())) {
			FoodMap_Meal_Id newId = new FoodMap_Meal_Id(req.getNewShopName(), meal.getMealName());
			meal = foodMapService.updateMealId(id, newId);
			return meal;
		}
		if (!StringUtils.hasText(req.getNewShopName()) && StringUtils.hasText(req.getNewMealName())) {
			FoodMap_Meal_Id newId = new FoodMap_Meal_Id(meal.getShopName(), req.getNewMealName());
			meal = foodMapService.updateMealId(id, newId);
			return meal;
		}
		return null;
	}
}
