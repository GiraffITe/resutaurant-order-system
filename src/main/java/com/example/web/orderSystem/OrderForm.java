package com.example.web.orderSystem;

public class OrderForm {

	private int menuNo;
	private String tableName;
	private String menuName;
	private int price;
	private int[] quantity;
	private int newQuantity;

	public OrderForm() {
		menuNo = 0;
		tableName = "";
		menuName = "";
		price = 0;
		quantity = null;
	}

	public int getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(int newQuantity) {
		this.newQuantity = newQuantity;
	}

	public int getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int[] getQuantity() {
		return quantity;
	}

	public void setQuantity(int[] quantity) {
		this.quantity = quantity;
	}

}
