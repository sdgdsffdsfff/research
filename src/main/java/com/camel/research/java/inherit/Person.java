package com.camel.research.java.inherit;

public class Person extends Human{
	
	/**
	 * 调用父类的保护方法
	 * @return
	 */
	public String getPersonName(){
		String name = getName();
		
		return name;
	}
	
	/**
	 * 直接调用父类，显性调用父类受保护方法
	 * @return
	 */
	public String getNameExplicit(){
		String name = Human.getName();
		return name;
	}
}
