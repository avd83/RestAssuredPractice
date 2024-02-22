package com.qa.tests;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BookAPITest {	
	String id;
	String name= "PlayWrite";
	String isbn= "PW";
	String aisle = "001";
	String author = "Jhone Thives";	
	
	@Test(priority=1)
	public void postAddBook() {

	RestAssured.baseURI ="https://rahulshettyacademy.com";		
		String response = given().log().all().header("Content-Type","application/json").
			body("{\r\n"
					+ "\r\n"
					+ "\"name\":\""+name+"\",\r\n"
					+ "\"isbn\":\""+isbn+"\",\r\n"
					+ "\"aisle\":\""+aisle+"\",\r\n"
					+ "\"author\":\""+author+"\"\r\n"
					+ "}\r\n"
					+ "")
		.when().log().all().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();		
		
		JsonPath js = new JsonPath(response);
		String message = js.getString("Msg");
		System.out.println(message);
		id = js.getString("ID");
		System.out.println(id);
	}
	
	@Test(priority=2)
	public void getBook() {
		RestAssured.baseURI ="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("ID",id)
		.when().log().all().get("/Library/GetBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
				
		JsonPath js = new JsonPath(response);	
		String bookName = js.getString("book_name");
		System.out.println(bookName);
		String authorName = js.getString("author");
		System.out.println(authorName);
		//Assert.assertEquals(name, bookName);
		//Assert.assertEquals(author, authorName);
	}
	
	@Test(priority=3)
	public void deleteBook() {
		
		RestAssured.baseURI ="https://rahulshettyacademy.com";		
		String response = given().log().all().header("Content-Type","application/json")
				.body("{ \r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ " } \r\n"
				+ "")
		.when().log().all().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String message = js.getString("msg");
		System.out.println(message);	
		Assert.assertEquals(message, "book is successfully deleted");		
	}
}
