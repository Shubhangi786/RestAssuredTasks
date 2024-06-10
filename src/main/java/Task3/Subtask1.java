package Task3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.equalTo;

public class Subtask1 {
	
	public static void main(String [] args) {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		
		
		RestAssured.given()
						.pathParam("id",12345)
					.when()
						.get("/pet/{id}")
					.then()
						.statusCode(200)
						.contentType(ContentType.JSON)
						.body("category.name", equalTo("dog"))
						.body("name", equalTo("snoopie"))
		                .body("status", equalTo("pending"));
		
		
		
		
	}

}
