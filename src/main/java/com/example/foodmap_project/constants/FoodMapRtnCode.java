package com.example.foodmap_project.constants;

public enum FoodMapRtnCode {

	SUCCESSFUL("200", "SUCCESSFUL"), 
	UPDATE_SUCCESSFUL("200", "SUCCESSFUL / price & meal_level 若=0 等同不修改"),

	SHOPNAME_CITY_REQUIRED("400", "Shop_name & City can not be null or empty"),
	NEWSHOPNAME_CITY_REQUIRED("400", "New_Shop_name & City can not be null or empty"),
	REQ_REQUIRED("400", "Request can not be null or empty"), 
	CITY_REQUIRED("400", "City can not be null or empty"),
	SHOPNAME_REQUIRED("400", "Shop_name can not be null or empty"),
	MEALNAME_REQUIRED("400", "Shop_name & Meal_name can not be null or empty"),

	CITY_INEXISTED("403", "city is not existed"), 
	SHOPNAME_EXISTED("403", "Shop_name is existed"),
	MEALNAME_EXISTED("403", "Shop_name & Meal_name is existed"),

	SHOPNAME_INEXISTED("403", "Shop_name is not existed(商店的資料庫中，不存在此商店名稱)"),
	MEALNAME_INEXISTED("403", "Shop_name & Meal_name is not existed"),

	DISPLAYAMOUNT_NEGATIVE("400", "DisplayAmount cannot be negative"),
	
	SHOPLEVEL_FAIL("400", "shoplevel is fail (must be 1-5)"), 
	MEALLEVEL_FAIL("400", "meallevel is fail (must be 1-5)"),
	PRICE_FAIL("400", "price is fail"),
	
	LEVEL_PRICE_FAILURE("400", "Level or Price can not be 0"),
	LEVEL_FAILURE("400", "Level is fail"),;

	private String code;
	private String message;

	private FoodMapRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
