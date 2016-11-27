package com.rongzhiheng.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;


public class CheckOut {
	
	public static boolean isEmpty(EditText editText) {   
		
		String checkString = editText.getText().toString().trim();
		if (checkString.length()==0) {
			return true; 
		} 
        return false;   
    } 
	
	public static boolean checkShenFenZheng(String shenFenZheng) {    
		Pattern p = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");  
		Matcher m = p.matcher(shenFenZheng);    
		return m.matches();   
	}
	
	public static boolean checkNameOrPassWord(String nameOrPassWord) {    
		Pattern p = Pattern.compile("[@A-Za-z0-9]{6,20}");  
		Matcher m = p.matcher(nameOrPassWord);    
		return m.matches();   
	} 
	
	public static boolean checkEmail(String email) {    
		Pattern p = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");  
		Matcher m = p.matcher(email);    
		return m.matches();   
	}
	
	public static boolean checkQQ(String qq) {    
		Pattern p = Pattern.compile("\\d{5,12}");  
		Matcher m = p.matcher(qq);    
		return m.matches();   
	}
	
	public static boolean isMobileNO(String mobiles) {   
//      Pattern p = Pattern.compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");   
//		Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[6-8]|8[0-9])\\d{8}$");  
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(mobiles);   
		System.out.println(m.matches() + "---");   
		return m.matches();   
	} 
	
}
