package com.example.web.orderSystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.MenuInfo;
import com.example.domain.OrderInfo;
import com.example.domain.RankingInfo;
import com.example.service.DBAccessService;

@Controller
public class OrderSystemController {

	@Autowired
	private DBAccessService dbAccessService;

	@ModelAttribute(value = "orderForm")
	public OrderForm setOrderForm() {
		return new OrderForm();
	}
	
	@ModelAttribute(value = "menuForm")
	public MenuForm setMenuForm() {
		return new MenuForm();
	}

	@RequestMapping(value = "/welcomeAction")
	public String welcome() {
		return "customerView/welcomeAction";
	}

	@RequestMapping(value = "/selectTable")
	public String test() {
		return "customerView/selectTable";
	}

	@RequestMapping(value = "/select-menu")
	public String selectMenu(@RequestParam("tableName") String tableName, Model model) {

		model.addAttribute("table", tableName);
		List<MenuInfo> menuList = dbAccessService.selectMenuList();
		model.addAttribute("menuList", menuList);
		return "customerView/selectMenu";
	}

	@RequestMapping(value = "/order-end")
	public String orderEnd(@RequestParam("tableName") String tableName, @ModelAttribute("orderForm") OrderForm form,
			Model model) {

		model.addAttribute("table", tableName);
		List<OrderForm> orderList = new ArrayList<>();

		int i = 1;
		for (int quantity : form.getQuantity()) {
			form = new OrderForm();
			if (quantity != 0) {
				form.setMenuNo(i);
				form.setNewQuantity(quantity);
				orderList.add(form);
			}
			i++;
		}

		for (OrderForm order : orderList) {
			order.setMenuName(dbAccessService.selectOrderMenuName(order.getMenuNo()));
			order.setPrice(dbAccessService.selectOrderMenuPrice(order.getMenuNo())*order.getNewQuantity());
		}

		dbAccessService.insertOrderInfo(tableName, orderList);
		dbAccessService.insertOnotherOrderInfo(tableName, orderList);
		model.addAttribute("orderList", orderList);

		return "customerView/orderEnd";
	}
	
	@RequestMapping(value="/bill")
	public String bill(@RequestParam("tableName")String tableName,Model model) {
		int bill = dbAccessService.billInfo(tableName);
		model.addAttribute("bill", bill);
		return "customerView/bill";
	}

	@RequestMapping(value = "/table-ranking")
	public String tableRanking(@RequestParam("tableName") String tableName, Model model) {

		List<RankingInfo> rankingInfo = dbAccessService.tableRankingInfo(tableName);

		int i = 1;
		for (RankingInfo ranking : rankingInfo) {
			ranking.setNumber(i);
			i++;
		}

		model.addAttribute("ranking", rankingInfo);

		return "customerView/tableRanking";

	}
	
	/**
	 * お店全体でのランキング表示機能
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/all-ranking")
	public String allRanking(Model model) {
		List<RankingInfo> rankingInfo = dbAccessService.allRankingInfo();
		
		int i = 1;
		for (RankingInfo ranking : rankingInfo) {
			ranking.setNumber(i);
			i++;
		}
		
		model.addAttribute("ranking", rankingInfo);

		return "customerView/allRanking";
	}
	/**
	 * テーブル別でのランキング表示機能
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menu-list")
	public String orderInfo(Model model) {
		List<MenuInfo> menu = dbAccessService.selectMenuList();
		
		model.addAttribute("menu", menu);
		
		return "ownerView/menuList";
	}
	
	@RequestMapping(value = "add-menu")
	public String addOrder(@ModelAttribute("MenuForm") MenuForm form, Model model) {
		
		MenuInfo menuInfo = new MenuInfo();
		BeanUtils.copyProperties(form, menuInfo);
		
		dbAccessService.addMenuInfo(menuInfo);
		
		List<MenuInfo> menu = dbAccessService.selectMenuList();
		model.addAttribute("menu", menu);
		
		return "ownerView/menuList";
		
	}
	
	/**
	 * オーナー側で閲覧できるオーダーリスト
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/order-list")
	public String menuList(Model model) {
		List<OrderInfo> orderList = dbAccessService.orderInfo();
		model.addAttribute("orderList", orderList);
		return "ownerView/orderList";
	}
	
	/**
	 * オーナー側での会計計算機能
	 * @param tableName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/owner-bill")
	public String ownerBill(@RequestParam("tableName") String tableName,Model model) {
		int bill = dbAccessService.billInfo(tableName);
		model.addAttribute("bill", bill);
		model.addAttribute("tableName",tableName);
		return "ownerView/bill";
	}
	
	/**
	 * 会計後、テーブルのオーダー情報を削除
	 * @param tableName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="reset-table-info")
	public String resetTabelInfo(@RequestParam("tableName")String tableName,Model model) {
		List<OrderInfo> orderList = dbAccessService.orderInfo();
		model.addAttribute("orderList", orderList);
		dbAccessService.deleteTableInfo(tableName);
		return "ownerView/orderList";
	}
	
	/**
	 * 変更メニュー修正画面遷移
	 */
	@RequestMapping(value="update-menu")
	public String updateMenu(@ModelAttribute("MenuForm")MenuForm menuForm,Model model) {
		MenuInfo menuInfo = dbAccessService.selectUpdateMenuInfo(menuForm.getMenuNo());
		model.addAttribute("menuInfo",menuInfo);
		return "ownerView/updateMenu";
	}
	
	/**
	 * 登録済みメニューの削除
	 */
	@RequestMapping(value="end-delete-menu")
	public String endDeleteMenu(@RequestParam("menuNo")int menuNo,Model model) {
		dbAccessService.deleteMenuInfo(menuNo);
		
		List<MenuInfo> menu = dbAccessService.selectMenuList();
		model.addAttribute("menu", menu);
		
		return "ownerView/menuList";
		
	}
	
	/**
	 * 登録済みメニューの修正
	 */
	@RequestMapping(value="end-update-menu")
	public String endUpdateMenu(@ModelAttribute("MenuForm")MenuForm menuForm,@RequestParam("menuNo")int menuNo,Model model) {
		MenuInfo menuInfo = new MenuInfo();
		BeanUtils.copyProperties(menuForm, menuInfo);
		menuInfo.setMenuNo(menuNo);
		
		dbAccessService.updateMenuInfo(menuInfo);
		List<MenuInfo> menu = dbAccessService.selectMenuList();
		model.addAttribute("menu", menu);
		
		return "ownerView/menuList";
		
	}
}
