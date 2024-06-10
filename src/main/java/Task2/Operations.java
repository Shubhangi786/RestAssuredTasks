package Task2;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Operations {
	
	public void verifyResponseCodeAndCount() {
		String [] endPointUrls = {"/posts","/comments","/albums","/photos","/todos","/users"};
		for(String endPoint : endPointUrls) {
			Response response = RestAssured.given().accept(ContentType.JSON).get(endPoint);
			int statusCode = response.getStatusCode();
			int resourceCount = response.jsonPath().getList("").size();
			
			System.out.println("------------Validate response for : "+ endPoint + "---------------");
			System.out.println("Status code : " + statusCode);
			Assert.assertEquals(200, statusCode);
			System.out.println("Total resource count : " + resourceCount);
			
			//Below code can be use to validate no. of resources
//			Assert.assertEquals(expectedCount, resourceCount);
		
		}
	}

	public void getResource(String resource) {
        Response response = RestAssured.given().accept(ContentType.JSON).get(resource);

        System.out.println("Resource: " + resource);
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("----------------------------------------");
    }
	
	public void updateResource(String resource, String requestBody) {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .body(requestBody)
                .put(resource);

        System.out.println("Resource: " + resource);
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("----------------------------------------");
    }
	
	public void deleteResource(String resource) {
        Response response = RestAssured.given().delete(resource);

        System.out.println("Resource: " + resource);
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("----------------------------------------");
    }

	public void createResource(String resource, String requestBody) {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .body(requestBody)
                .post(resource);

        System.out.println("Resource: " + resource);
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("----------------------------------------");
    }
}
