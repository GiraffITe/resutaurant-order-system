package com.example.persistence;

import java.util.List;

import com.example.domain.MenuInfo;
import com.example.domain.OrderInfo;
import com.example.domain.RankingInfo;

public interface MenuInfoMapper {
	
	/**
	 *メニューリストの検索（全件検索）
	 * @return
	 */
	public List<MenuInfo> selectMenuInfo();
	/**
	 * 注文があったメニューの情報を取得
	 */
	public String selectOrderInfo(int menuNo);
	public int selectOrderPrice(int menuNo);
	public void insertOrderMenu(String tableName,String menuName,int quantity,int price);
	public void insertOnotherOrderMenu(String tableName,String menuName,int quantity,int price);
	
	/**
	 * 注文ランキングを取得
	 */
	public List<RankingInfo> tableRanking(String tableName);
	public List<RankingInfo> allRanking();
	
	/**
	 * オーダーリストを取得
	 * @return
	 */
	public List<OrderInfo> allOrder();
	
	/**
	 * 新メニューの追加
	 */
	public void addMenu(MenuInfo menuInfo);
	
	/**
	 * お会計確認
	 */
	public int bill(String tableName);
	
	/**
	 * テーブル別オーダー情報削除
	 */
	public int deleteTableOrderInfo(String tableName);
	
	/**
	 * 修正するメニュー情報の取得
	 */
	public MenuInfo selectUpdateMenu(int menuNo);
	/**
	 * 登録済みメニューの削除
	 */
	public void deleteMenu(int menuNo);
	
	/**
	 * 登録済みメニューの変更
	 * @param menuInfo
	 * @param menuNo
	 */
	public void updateMenu(MenuInfo menuInfo);
}

