package com.inswork.indexbar.bank;

import java.util.List;

public class BankBean implements Comparable<BankBean> {





	private String name;
	private String bankcode;//银行代码
	private List<String> info;

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	private int icon;
	private int iconUrl;//图标网络地址

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public int getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(int iconUrl) {
		this.iconUrl = iconUrl;
	}

	private String pinyin;

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public BankBean(String name, int icon) {
		super();
		this.name = name;
		this.icon = icon;
		pinyin = PinYinUtil.getPinYin(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(BankBean another) {
		return this.pinyin.compareTo(another.getPinyin());
	}

	public List<String> getInfo() {
		return info;
	}

	public void setInfo(List<String> info) {
		this.info = info;
	}
}
