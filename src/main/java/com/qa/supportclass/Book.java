package com.qa.supportclass;

public class Book {
	static String name= "Python";
	static String isbn= "PY";
	static String aisle = "011";
	static String author = "Sam Joy";	
	
	public static String addPayloadRequest(){
		
		 String query = "{\r\n"
					+ "\r\n"
					+ "\"name\":\""+name+"\",\r\n"
					+ "\"isbn\":\""+isbn+"\",\r\n"
					+ "\"aisle\":\""+aisle+"\",\r\n"
					+ "\"author\":\""+author+"\"\r\n"
					+ "}\r\n"
					+ "";
		 return query;			
	}	
	public static String deletePayloadReuest(String id) {
		
		String deleteQuery = "{ \r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ " } \r\n"
				+ "";
		return deleteQuery;
	}
}
