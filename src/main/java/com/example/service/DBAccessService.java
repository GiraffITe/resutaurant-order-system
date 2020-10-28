package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.MenuInfo;
import com.example.domain.OrderInfo;
import com.example.domain.RankingInfo;
import com.example.persistence.MenuInfoMapper;
import com.example.web.orderSystem.OrderForm;

@Service
public class DBAccessService {
	
	@Autowired
	private MenuInfoMapper mapper;
	
	public List<MenuInfo> selectMenuList() {
		List<MenuInfo> list = mapper.selectMenuInfo();
		return list;
	}
	
	public String selectOrderMenuName(int menuNo){
		String menuName = mapper.selectOrderInfo(menuNo);
		return menuName;
	}
	
	public int selectOrderMenuPrice(int menuNo) {
		int menuPrice = mapper.selectOrderPrice(menuNo);
		return menuPrice;
	}
	
	public void insertOrderInfo(String tableName ,List<OrderForm> list) {
		for(OrderForm order : list) {
			mapper.insertOrderMenu(tableName, order.getMenuName()
					,order.getNewQuantity(),order.getPrice());
		}
		
	}
	public void insertOnotherOrderInfo(String tableName ,List<OrderForm> list) {
		for(OrderForm order : list) {
			mapper.insertOnotherOrderMenu(tableName, order.getMenuName()
					,order.getNewQuantity(),order.getPrice());
		}
		
	}
	
	
	/**
	 * テーブルランキングを取得
	 * @param tableName
	 * @return
	 */
	public List<RankingInfo> tableRankingInfo(String tableName) {
		List<RankingInfo> ranking = mapper.tableRanking(tableName);
		return ranking;
	}
	
	/**
	 * 全体のランキング取得
	 */
	public List<RankingInfo> allRankingInfo(){
		List<RankingInfo> ranking = mapper.allRanking();
		return ranking;
	}
	/**
	 * すべてのオーダーを取得
	 */
	public List<OrderInfo> orderInfo(){
		List<OrderInfo> order = mapper.allOrder();
		return order;
	}
	/**
	 * メニューの追加
	 */
	public void addMenuInfo(MenuInfo menuInfo) {
		mapper.addMenu(menuInfo); 
	}
	/**
	 * お会計確認情報取得
	 */
	public int billInfo(String tableName) {
		int bill = mapper.bill(tableName);
		return bill;
	}
	
	/**
	 * テーブル別オーダー情報削除
	 */ 
	public void deleteTableInfo(String tableName) {
		mapper.deleteTableOrderInfo(tableName);
	}
	/**
	 * 修正するメニュー情報を取得
	 */
	public MenuInfo selectUpdateMenuInfo(int menuNo) {
		MenuInfo menu = mapper.selectUpdateMenu(menuNo);
		return menu;
	}
	/**
	 * 登録済みメニューの削除
	 */
	public void deleteMenuInfo(int menuNo) {
		mapper.deleteMenu(menuNo);
	}
	/**
	 * 登録済みメニューの変更
	 */
	public void updateMenuInfo(MenuInfo menuInfo) {
		mapper.updateMenu(menuInfo);
	}
}
