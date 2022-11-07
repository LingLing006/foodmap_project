package com.example.foodmap_project.constants;

public enum FoodMapRtnCode {

	SUCCESSFUL("200","SUCCESSFUL"),
	SHOPNAME_CITY_REQUIRED("400","Shop_name & City can not be null or empty"),
	CITY_REQUIRED("400","City can not be null or empty"),
	SHOPNAME_REQUIRED("400","Shop_name can not be null or empty"),
	MEALNAME_REQUIRED("400","Shop_name & Meal_name can not be null or empty"),
	PRICE_NEGATIVE("400","price cannot be negative"),
	DISPLAYAMOUNT_NEGATIVE("400","DisplayAmount cannot be negative"),
	SHOPLEVEL_FAIL("400","shoplevel is fail"),
	MEALLEVEL_FAIL("400","meallevel is fail"),
	SHOPNAME_EXISTED("403","Shop_name is existed"),
	MEALNAME_EXISTED("403","Shop_name & Meal_name is existed"),
	SHOPNAME_INEXISTED("403","Shop_name is not existed"),
	CITY_INEXISTED("403","city is not existed"),
	MEALNAME_INEXISTED("403","Shop_name & Meal_name is not existed"),
	LEVEL_FAILURE("400","Level is fail"),
	ADD_ROLE_FAILURE("500","role is fail");
	
	private String code;
	private String message;
	
	private FoodMapRtnCode(String code,String message) {
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
