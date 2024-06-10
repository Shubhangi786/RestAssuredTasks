package Task2;

import org.junit.Assert;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ValidateResponse {
	
	
	public static void main(String [] args) {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		Operations crudOps = new Operations();
		
		//Subtask -1
		System.out.println("------------------- Subtask-1-----------------------");
		crudOps.verifyResponseCodeAndCount();
		
		//Subtask -2
		System.out.println("\n\n------------------- Subtask-2-----------------------");
		//GET
		crudOps.getResource("/posts/5");
		crudOps.getResource("/comments/5");
		crudOps.getResource("/albums/5");
		crudOps.getResource("/photos/5");
		crudOps.getResource("/todos/5");
		crudOps.getResource("/users/5");
		
		//Modify
		crudOps.updateResource("/posts/5", "{\"title\": \"Modified title by update operation\"}");
		crudOps.updateResource("/comments/5","{\"name\": \"Modified name by Update operation\"}");
		crudOps.updateResource("/albums/5","{\"title\": \"Modified name by Update operation\"}");
		crudOps.updateResource("/photos/5","{\"title\": \"Modified name by Update operation\"}");
		crudOps.updateResource("/todos/5","{\"completed\": true}");
		crudOps.updateResource("/users/5","{\"name\": \"Modified name by Update operation\"}");
		
		//Delete
		crudOps.deleteResource("/posts/5");
		crudOps.deleteResource("/comments/5");
		crudOps.deleteResource("/albums/5");
		crudOps.deleteResource("/photos/5");
		crudOps.deleteResource("/todos/5");
		crudOps.deleteResource("/users/5");
		
		//Create
		crudOps.createResource("/posts", "{\"title\": \"New Post\", \"body\": \"Created by operation\", \"userId\": 5}");
		crudOps.createResource("/comments", "{\"name\": \"New Comment added by operation\", \"email\": \"operations@example.com\", \"body\": \"New Body by create operation\", \"postId\": 5}");
		crudOps.createResource("/albums","{\"title\": \"New Album added by operation\", \"userId\": 5}");
		crudOps.createResource("/photos","{\"title\": \"New Photo added by operation\", \"url\": \"https://example.com/photo.jpg\", \"thumbnailUrl\": \"https://example.com/photo-thumb.jpg\", \"albumId\": 5}");
		crudOps.createResource("/todos","{\"title\": \"New Todo added by operation\", \"completed\": false, \"userId\": 5}");
		crudOps.createResource("/users", "{\"name\": \"New User added by operation\", \"email\": \"new@example.com\"}");
		
	}
		
}
