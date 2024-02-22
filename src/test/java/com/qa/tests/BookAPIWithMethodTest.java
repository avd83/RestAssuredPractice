package com.qa.tests;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.endpoint.Endpoint;
import com.qa.supportclass.Book;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BookAPIWithMethodTest {	
	String id;
	
	@Test(priority=1)
	public void postAddBook() {

	RestAssured.baseURI = Endpoint.baseURI;		
		String response = given().log().all().header("Content-Type","application/json").
			body(Book.addPayloadRequest())
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
		RestAssured.baseURI =Endpoint.baseURI;	
		String response = given().log().all().queryParam("ID",id)
		.when().log().all().get(Endpoint.getResource)
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
		
		RestAssured.baseURI =Endpoint.baseURI;		
		String response = given().log().all().header("Content-Type","application/json")
				.body(Book.deletePayloadReuest(id))
		.when().log().all().post(Endpoint.deleteResource)
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String message = js.getString("msg");
		System.out.println(message);	
		Assert.assertEquals(message, "book is successfully deleted");		
	}
}
