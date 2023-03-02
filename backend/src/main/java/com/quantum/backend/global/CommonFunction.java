package com.quantum.backend.global;

import java.text.SimpleDateFormat;

public class CommonFunction 
{
	public static void println(String s)
	{
		System.out.println(s);
	}
	
	public static void print(String s)
	{
		System.out.print(s);
	}
		
	public static void printlnTime(String s)
	{
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String currentTime = new SimpleDateFormat("HH:mm:ss.SSSXXX").format(new java.util.Date());
		String currentDateTime = currentDate + "T" + currentTime;
		System.out.println(currentDateTime + " - " + s);
	}
}
