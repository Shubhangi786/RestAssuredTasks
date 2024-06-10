package Task3;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;


import io.restassured.RestAssured;

public class Subtask2 {
	public static void main(String [] args) {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		
		
		RestAssured.given()
						.pathParam("endpoint","users")
					.when()
						.get("/{endpoint}")
					.then()
						.statusCode(200)
			            .assertThat()
			            .body("size()", greaterThan(3))
			            .body("name", hasItem("Ervin Howell"));
		}
	}