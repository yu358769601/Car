package com.communication;

import java.util.ArrayList;

public class CommunicationList {
	private String CommunicationListId;
	private String CommunicationListPhoto; 
	private String CommunicationListName; 
	private String CommunicationListPhone;
	private String CommunicationListDir1_title;
	private String CommnuicationListDir2_title;
	private String CommnuicationListZhiwei;
	private String CommunicationListSx;	
	
	static ArrayList<CommunicationList> CommunicationListList = new ArrayList<CommunicationList>();
	static CommunicationList mCommunicationList;
	public CommunicationList(String CommunicationListId, 
			String CommunicationListPhoto,
			String CommunicationListName,
			String CommunicationListPhone,
			String CommunicationListDir1_title,
			String CommunicationListDir2_title,
			String CommunicationListZhiwei,			
			String CommunicationListSx) {
		this.CommunicationListId = CommunicationListId;
		this.CommunicationListPhoto = CommunicationListPhoto;
		this.CommunicationListName = CommunicationListName; 
		this.CommunicationListPhone = CommunicationListPhone;
		this.CommunicationListDir1_title = CommunicationListDir1_title;
		this.CommnuicationListDir2_title = CommunicationListDir2_title;
		this.CommnuicationListZhiwei = CommunicationListZhiwei;
		this.CommunicationListSx = CommunicationListSx;

	}

	public String getCommunicationListId() {
		return CommunicationListId;
	}

	public void setCommunicationListId(String communicationListId) {
		CommunicationListId = communicationListId;
	}
	public String getCommunicationListPhoto() {
		return CommunicationListPhoto;
	}

	public void setCommunicationListPhoto(String communicationListPhoto) {
		CommunicationListPhoto = communicationListPhoto;
	}
	public String getCommunicationListName() {
		return CommunicationListName;
	}

	public void setCommunicationListName(String communicationListName) {
		CommunicationListName = communicationListName;
	}
	public String getCommunicationListPhone() {
		return CommunicationListPhone;
	}

	public void setCommunicationListPhone(String communicationListPhone) {
		CommunicationListPhone = communicationListPhone;
	}
	public String getCommunicationListSx() {
		return CommunicationListSx;
	}

	public void setCommunicationListSx(String communicationListSx) {
		CommunicationListSx = communicationListSx;
	} 
	public String getCommunicationListDir1_title() {
		return CommunicationListDir1_title;
	}

	public void setCommunicationListDir1_title(String communicationListDir1_title) {
		CommunicationListDir1_title = communicationListDir1_title;
	}

	public String getCommnuicationListDir2_title() {
		return CommnuicationListDir2_title;
	}

	public void setCommnuicationListDir2_title(String commnuicationListDir2_title) {
		CommnuicationListDir2_title = commnuicationListDir2_title;
	}

	public String getCommnuicationListZhiwei() {
		return CommnuicationListZhiwei;
	}

	public void setCommnuicationListZhiwei(String commnuicationListZhiwei) {
		CommnuicationListZhiwei = commnuicationListZhiwei;
	}
	
	public static void setCommunicatList(String user_id,String photo, String names,String phone,
			String dir1_title, String dir2_title, String zhiwei, String piyin) {
		// TODO Auto-generated method stub  
			mCommunicationList = new CommunicationList(user_id,photo,names,phone,dir1_title,dir2_title,zhiwei,piyin);
			CommunicationListList.add(mCommunicationList); 
	}
	public static ArrayList<CommunicationList> getCommunicatList() { 
		return CommunicationListList;
	}
	
	
//	public static ArrayList<CommunicationList> getCityList() {
//
//		ArrayList<CommunicationList> CommunicationListList = new ArrayList<CommunicationList>();
//		// 热门城市
//		CommunicationList mCommunicationList;
//		// a
//		mCommunicationList = new CommunicationList("12", "安徽省", "AHS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("33", "澳门特别行政区", "AM");
//		CommunicationListList.add(mCommunicationList);
//		// b
//		mCommunicationList = new CommunicationList("1", "北京市", "BJS");
//		CommunicationListList.add(mCommunicationList);
//		// c
//		mCommunicationList = new CommunicationList("22", "重庆省", "CQS");
//		CommunicationListList.add(mCommunicationList);
//		// d
//		// e
//		// f
//		mCommunicationList = new CommunicationList("13", "福建省", "FJS");
//		CommunicationListList.add(mCommunicationList);
//		// g
//		mCommunicationList = new CommunicationList("20", "广西壮族自治区", "GXZZZZQ");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("19", "广东省", "GDS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("24", "贵州省", "GZS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("28", "甘肃省", "GSS");
//		CommunicationListList.add(mCommunicationList);
//		// h
//		mCommunicationList = new CommunicationList("8", "黑龙江省", "HLJS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("3", "河北省", "HBS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("16", "河南省", "HNS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("17", "湖北省", "HBS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("18", "湖南省", "HNS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("21", "海南省", "HNS");
//		CommunicationListList.add(mCommunicationList);
//		// i
//		// j
//		mCommunicationList = new CommunicationList("7", "吉林省", "JLS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("10", "江苏省", "JSS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("14", "江西省", "JSS");
//		CommunicationListList.add(mCommunicationList);
//		// k
//		// l
//		mCommunicationList = new CommunicationList("6", "辽宁省", "LNS");
//		CommunicationListList.add(mCommunicationList);
//		// m
//		// n
//		mCommunicationList = new CommunicationList("5", "内蒙古自治区", "NMGZZQ");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("30", "宁夏回族自治区", "NXHZZZQ");
//		CommunicationListList.add(mCommunicationList);
//		// o
//		// p
//		// q
//		mCommunicationList = new CommunicationList("29", "青海省", "QHS");
//		CommunicationListList.add(mCommunicationList);
//		// r
//		// s
//		mCommunicationList = new CommunicationList("4", "山西省", "SXS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("9", "上海市", "SHS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("15", "山东省", "SDS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("23", "四川省", "SCS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("27", "陕西省", "SXS");
//		CommunicationListList.add(mCommunicationList);
//		// t
//		mCommunicationList = new CommunicationList("2", "天津市", "TJS");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("34", "台湾省", "TW");
//		CommunicationListList.add(mCommunicationList);
//		// u
//		// v
//		// w
//		// x
//		mCommunicationList = new CommunicationList("26", "西藏自治区", "XCZZQ");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("31", "新疆维吾尔自治区", "XJWWEZZQ");
//		CommunicationListList.add(mCommunicationList);
//		mCommunicationList = new CommunicationList("32", "香港特别行政区", "XG");
//		CommunicationListList.add(mCommunicationList);
//		// y
//		mCommunicationList = new CommunicationList("25", "云南省", "YNS");
//		CommunicationListList.add(mCommunicationList);
//		// z
//		mCommunicationList = new CommunicationList("11", "浙江省", "ZJS");
//		CommunicationListList.add(mCommunicationList); 
//		return CommunicationListList; 
//	}
 
	
}
