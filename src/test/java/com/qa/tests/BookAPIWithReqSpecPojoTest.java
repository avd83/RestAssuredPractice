package com.qa.tests;

import static io.restassured.RestAssured.given;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.a.pojo.AddBookRequestPojo;
import com.a.pojo.AddBookResponsePojo;
import com.a.pojo.DeleteBookResponsePojo;
import com.a.pojo.GetBookResponsePojo;
import com.qa.endpoint.Endpoint;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BookAPIWithReqSpecPojoTest {
	
	String id;
	String addBookMessage;
	AddBookRequestPojo addBookRequestPojo = new AddBookRequestPojo();
	AddBookResponsePojo addBookResponsePojo = new AddBookResponsePojo();

	@Test(priority=1)
	public void addBook() {

		String actualMessage = "successfully added";
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri(Endpoint.baseURI)
				.setContentType(ContentType.JSON).build();

		AddBookResponsePojo response = given().log().all().spec(reqSpec).body(addBookRequestPojo)
				.when().log().all().post(Endpoint.addResource)
				.then().log().all().assertThat().statusCode(200).extract().response().getBody()
				.as(AddBookResponsePojo.class);
		
		id = response.getId();
		System.out.println(id);
		addBookMessage = response.getMessage();
		System.out.println(addBookMessage);
		if(actualMessage.equals(addBookMessage)){			
			Assert.assertEquals(addBookMessage, actualMessage);
		}else {
			System.out.println("Negative validtaion : Book Already Exists");			
		}		
	}
	
	@Test(priority=2)
	public void getBook(){
		
		RequestSpecification resSpec = new RequestSpecBuilder().setBaseUri(Endpoint.baseURI).build();
				
		GetBookResponsePojo[] response = 	given().log().all().spec(resSpec).queryParam("ID",id)
					.when().log().all().get(Endpoint.getResource)
					.then().log().all().assertThat().statusCode(200).extract().response().getBody()
					.as(GetBookResponsePojo[].class);
		
		String bookName = response[0].getBook_name();
		System.out.println(bookName);
		String authorName = response[0].getAuthor();	
		System.out.println(authorName);
		
		Assert.assertEquals(addBookRequestPojo.getName(), bookName);
		Assert.assertEquals(addBookRequestPojo.getAuthor(), authorName);		
	}
	
	@Test(priority=3)
	public void deleteBook(){
		addBookResponsePojo.setId(id);
		String expectedDeleteMessage = "book is successfully deleted";
		RequestSpecification resReq = new RequestSpecBuilder().setBaseUri(Endpoint.baseURI)
					.setContentType(ContentType.JSON).build();
		
		DeleteBookResponsePojo response = given().log().all().spec(resReq).body(addBookResponsePojo)
				.when().log().all().post(Endpoint.deleteResource)
				.then().log().all().assertThat().statusCode(200).extract().response().getBody()
				.as(DeleteBookResponsePojo.class);
		
		String actualDeleteMessage = response.getMsg();
		System.out.println(actualDeleteMessage);
		Assert.assertEquals(expectedDeleteMessage, actualDeleteMessage);		
	}
	
	@BeforeMethod
	public void setUpData() {
		
		addBookRequestPojo.setName("JMeter");
		addBookRequestPojo.setIsbn("JM");
		addBookRequestPojo.setAisle("005");
		addBookRequestPojo.setAuthor("Tom Letham");		
	}

}
