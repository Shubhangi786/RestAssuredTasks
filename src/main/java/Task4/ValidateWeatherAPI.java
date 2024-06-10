package Task4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class ValidateWeatherAPI {
	
	static String apiKey;
	
	public static void main(String [] args) {
		RestAssured.baseURI = "http://api.openweathermap.org/data/2.5/weather";
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src\\main\\java\\Task4\\config.properties"));
			apiKey = (String) prop.get("appid");
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		
		Response response1 = given()
								.accept(ContentType.JSON)
								.queryParam("q", "hyderabad")
								.queryParam("appid", apiKey)
					       .when()
					       		.get();
			
		String lattitude = response1.jsonPath().getString("coord.lat");
		String longitude = response1.jsonPath().getString("coord.lon");
		
		Response response2 = given()
								.accept(ContentType.JSON)
								.queryParam("lat", lattitude)
								.queryParam("lon", longitude)
								.queryParam("appid", apiKey)
							.when()
									.get();
	
		response2.then().log().all()
					.body("name", equalTo("Hyderabad"))
					.body("sys.country", equalTo("IN"))
					.body("main.temp_min", greaterThan(0F))
					.body("main.temp", greaterThan(0F));
		
               
	}
}
	
