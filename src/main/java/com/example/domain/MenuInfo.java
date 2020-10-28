package com.example.domain;

import java.io.Serializable;

public class MenuInfo implements Serializable {

	private int menuNo;
	private String menuName;
	private int price;

	private static final long serialVersionUID = 1L;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
