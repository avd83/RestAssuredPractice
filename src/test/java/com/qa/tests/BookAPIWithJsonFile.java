package com.qa.tests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import com.qa.endpoint.Endpoint;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BookAPIWithJsonFile{

	@Test
	public void addBook() throws IOException {
		RestAssured.baseURI=Endpoint.baseURI;		
		String response = given().log().all().header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("F:\\15dec22_laptop\\REST Assured API\\data.json"))))
		.when().log().all().post(Endpoint.addResource)
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);//parsing from raw string to Json
		String id = js.getString("ID");
		System.out.println(id);
		
/*
	String path = "C:\\Users\\Abhay\\eclipse-workspace-RestAssured\\RestAssuredPractice\\src\\main\\java\\com\\qa\\files\\data.json";
		File f = new File(path);
		FileReader fr = new FileReader(f);
		JSONTokener jt = new JSONTokener(fr);
		JSONObject data = new JSONObject(jt);		
	
		
		RestAssured.baseURI = Endpoint.baseURI;
		String response = given().log().all().header("Content-Type","application/json")
				.body(data.toString())
				.when().log().all().post(Endpoint.addResource)
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id = js.getString("ID");
		System.out.println(id);
		*/
	}
}
