package com.qa.tests;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.a.pojo.AddBookRequestPojo;
import com.a.pojo.AddBookResponsePojo;
import com.a.pojo.DeleteBookResponsePojo;
import com.a.pojo.GetBookResponsePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qa.endpoint.Endpoint;
import io.restassured.RestAssured;


public class BookAPIWithPojoTest {	
String id;
String addedMessage ;
String deleteBookMessage;

AddBookRequestPojo addBookRequest = new AddBookRequestPojo();
AddBookResponsePojo addBookResponse = new AddBookResponsePojo();

	@Test(priority=1)
	public void postAddBook() {
		
	RestAssured.baseURI = Endpoint.baseURI;		
		AddBookResponsePojo response = given().log().all().header("Content-Type","application/json")
		.body(addBookRequest)
		.when().log().all().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().getBody().as(AddBookResponsePojo.class);	
		
		System.out.println(response);		
		id = response.getId();
		addedMessage = response.getMessage();
		System.out.println(addedMessage);
		System.out.println(id);
	}
		
	@Test(priority=2)
	public void getBook() throws JsonProcessingException {
		
		RestAssured.baseURI =Endpoint.baseURI;	
		GetBookResponsePojo[]  response = given().log().all().queryParam("ID",id)
		.when().log().all().get(Endpoint.getResource)
		.then().log().all().assertThat().statusCode(200).extract().response().getBody()
		.as(GetBookResponsePojo[].class);
					
		String bookName = response[0].getBook_name();
		System.out.println(bookName);
		String authorName = response[0].getAuthor();
		System.out.println(authorName);		
		Assert.assertEquals(addBookRequest.getName(), bookName);
		Assert.assertEquals(addBookRequest.getAuthor(), authorName);
	}
	
	@Test(priority=3)
	public void deleteBook() {
		addBookResponse.setId(id);
		RestAssured.baseURI =Endpoint.baseURI;		
		DeleteBookResponsePojo response = given().log().all().header("Content-Type","application/json")
				.body(addBookResponse)
		.when().log().all().post(Endpoint.deleteResource)
		.then().log().all().assertThat().statusCode(200).extract().response().getBody().as(DeleteBookResponsePojo.class);
		
		System.out.println(response);
		deleteBookMessage = response.getMsg();
		System.out.println(deleteBookMessage);
		Assert.assertEquals(deleteBookMessage,"book is successfully deleted");		
	}	
	@BeforeClass
	public void setUpData() {
		addBookRequest.setName("Cypress");
		addBookRequest.setIsbn("CY");
		addBookRequest.setAisle("003");
		addBookRequest.setAuthor("Rock Anthony");		
	}
}
