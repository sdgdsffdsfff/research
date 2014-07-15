package com.camel.research.java.inherit;

import java.sql.SQLException;

/**
 * 抽象父类
 * @author cameldeng
 * @since 2013年7月17日
 */
public abstract class Human {
	private String name;
	
	/**
	 * 受保护的方法
	 * @return
	 */
	protected static String getName(){
		return "human";
	}
	
	/**
	 * 公共方法
	 * @return
	 */
	public static String getSex(){
		return "M";
	}
	
	public static void main (String[] args){
	    Exception a = new SQLException();
	    boolean rs = a instanceof SQLException;
	    boolean rs1 = a instanceof RuntimeException;
	    boolean rs2 = a instanceof Exception;
	    System.out.println(rs);
	    System.out.println(rs1);
	    System.out.println(rs2);
	}
}
