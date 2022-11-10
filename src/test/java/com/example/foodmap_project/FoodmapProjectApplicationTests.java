package com.example.foodmap_project;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.entity.FoodMapShop;
import com.example.foodmap_project.repository.FoodMapMealDao;
import com.example.foodmap_project.repository.FoodMapShopDao;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapListResponse;

@SpringBootTest(classes = FoodmapProjectApplication.class)
class FoodmapProjectApplicationTests {

	@Autowired
	private FoodMapMealDao foodMapMealDao;
	
	@Autowired
	private FoodMapShopDao foodMapShopDao;
	
	@Autowired
	private FoodMapService foodMapService;

	@Test
	public void contextLoads() {
		
		
		
//		FoodMapShop shop = foodMapService.updateShopName("AAA", "KFC");
//		System.out.println(shop.getShopName()+"  "+shop.getShopLevel());
		
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
		
		List<FoodMapShop> shopList = foodMapShopDao.findByShopLevelGreaterThanOrderByShopLevelDesc(3);
		for(FoodMapShop shop :shopList) {
			System.out.printf("shopname:%s city:%s shopLevel:%.2f\n",shop.getShopName(),shop.getCity(),shop.getShopLevel());

		}
		
//		FoodMapMealId id = new FoodMapMealId("Starbucks","coffee");
//		FoodMapMeal meal = foodMapService.updateMealName(id, "pancake");
//		System.out.println(meal.getShopName()+ "    "+ meal.getMealName()+"    "+meal.getMealLevel()+"    "+meal.getPrice());
//		
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
