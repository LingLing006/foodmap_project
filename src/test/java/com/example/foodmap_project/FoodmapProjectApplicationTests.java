package com.example.foodmap_project;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Meal_Id;
import com.example.foodmap_project.entity.FoodMap_Shop;
import com.example.foodmap_project.repository.FoodMap_Meal_Dao;
import com.example.foodmap_project.repository.FoodMap_Shop_Dao;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapListResponse;

@SpringBootTest(classes = FoodmapProjectApplication.class)
class FoodmapProjectApplicationTests {

	@Autowired
	private FoodMap_Meal_Dao foodMapMealDao;
	
	@Autowired
	private FoodMap_Shop_Dao foodMapShopDao;
	
	@Autowired
	private FoodMapService foodMapService;

	@Test
	public void contextLoads() {
		
		FoodMap_Shop shop = foodMapService.updateShopName("AAA", "KFC");
		System.out.println(shop.getShopName()+"  "+shop.getShopLevel());
		
//		List<FoodMap_Shop> shopList = foodMapShopDao.findTop3ByCityOrderByShopLevelDesc("Keelung");
//		for(FoodMap_Shop shop :shopList ) {
//			System.out.printf("shopname:%s city:%s shopLevel:%.2f\n",shop.getShopName(),shop.getCity(),shop.getShopLevel());
//		}
		
//		List<FoodMap_Shop> shopList = foodMapShopDao.findAllByOrderByShopLevelDesc();
//		for(FoodMap_Shop shop :shopList ) {
//			System.out.printf("shopname:%s city:%s shopLevel:%.2f\n",shop.getShopName(),shop.getCity(),shop.getShopLevel());
//		}
		
//		List<FoodMap_Meal> mealList = foodMapMealDao.findByMealLevelGreaterThan(3);
//		for(FoodMap_Meal meal :mealList ) {
//			System.out.printf("shopname:%s mealname:%s mealLevel:%d\n",meal.getShopName(),meal.getMealName(),meal.getMealLevel());
//		}
		
		
	}
	@Test
	public void test() {
		FoodMap_Meal_Id id = new FoodMap_Meal_Id("Starbucks","coffee");
		FoodMap_Meal meal = foodMapService.updateMealName(id, "pancake");
		System.out.println(meal.getShopName()+ "    "+ meal.getMealName()+"    "+meal.getMealLevel()+"    "+meal.getPrice());
		
//		FoodMapListResponse listRes = foodMapService.findShopByCity("Keelung", 3);
//		System.out.println("City"+listRes.getCity());
//		System.out.println(listRes.getResList());
		
//		List<FoodMap_Shop> list = foodMapShopDao.findAll(Sort.by(Sort.Direction.DESC, "shopLevel"));
//		
//		System.out.println(list);
//		List<FoodMap_Meal> mealList = foodMapMealDao.findBymealLevelGreaterThan(3);
//		for(FoodMap_Meal shop :mealList ) {
//		System.out.println(shop.getShopName()+"   "+shop.getMealName()+"   "+shop.getMealLevel());
//		}
	}
	
	

}
