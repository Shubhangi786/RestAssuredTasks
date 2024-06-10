package JiraAutomation;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonValue;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;


public class ValidateJiraAPI {

    private static final String BASE_URL = "https://shubhangimadhukarsable.atlassian.net";
    static String userAuthToken;
    private static RequestSpecification reqSpecification;
    static String basePath = "/rest/api/3/issue/";

    public static void main(String[] args) {
    	
    	Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src\\main\\java\\JiraAutomation\\config.properties"));
			userAuthToken = (String) prop.get("authToken");
		} catch (Exception e) {
			System.out.println(e);
		} 
		
        reqSpecification = RestAssured
        		.given()
        		.baseUri(BASE_URL)
        		.basePath(basePath)
        		.header("Authorization", userAuthToken)
        		.header("Accept", "application/json")
        		.header("Content-Type", "application/json");

        // Step 1: Create the defect in Jira
        String issueKey = createDefect();

        // Step 2: Update the Defect using defect ID
        updateDefect(issueKey);

        // Step 3: Search the Defect created in step 1
        searchDefect(issueKey);

        // Step 4: Add an attachment to the issue
        addAttachment(issueKey);

        // Step 5: Delete the defect created in step 1
        deleteDefect(issueKey);
    }
    

    private static String createDefect() {
    	
        JSONObject content = new JSONObject();
        JSONObject paragraph = new JSONObject();
        JSONObject text = new JSONObject();

        text.put("text", "Testing functional API").put("type", "text");
        paragraph.put("type", "paragraph");
        paragraph.put("content", new JSONObject[]{text});
        content.put("type", "doc");
        content.put("version", 1);
        content.put("content", new JSONObject[]{paragraph});
    	
    	JSONObject req =new JSONObject();
    	JSONObject fields = new JSONObject();
    	fields.put("project", new JSONObject().put("key","KANTEST"));
    	fields.put("summary", "Test summary for bug created by automation");
    	fields.put("reporter", "shubhangi.sable");
    	fields.put("description",content);
    	fields.put("issuetype", new JSONObject().put("id","10006"));
    	fields.put("reporter", new JSONObject().put("id", "712020:723ddee3-caac-4802-bf4b-22ca22f8e653"));
    	req.put("fields", fields);
    	JsonPath path = JsonPath.from(req.toString());
    	System.out.println(path.prettify());

        // Send the POST request using RestAssured
        Response response = reqSpecification.body(req.toString()).post();
        System.out.println(response.statusCode());
        // Print the response
        System.out.println(response.getBody().asPrettyString());
        String issueKey = response.jsonPath().getString("key");
        System.out.println("Bug created with key: " + issueKey);
        return issueKey;
    }
    


    private static void updateDefect(String issueKey) {
        JSONObject content = new JSONObject();
        JSONObject paragraph = new JSONObject();
        JSONObject text = new JSONObject();

        text.put("text", "Updated defect through Jira API").put("type", "text");
        paragraph.put("type", "paragraph");
        paragraph.put("content", new JSONObject[]{text});
        content.put("type", "doc");
        content.put("version", 1);
        content.put("content", new JSONObject[]{paragraph});

        JSONObject requestParams = new JSONObject();
        requestParams.put("fields", new JSONObject()
                .put("summary", "Updated bug Summary for trial")
                .put("description", content));
        
        System.out.println(requestParams.toString());
        
        Response response = reqSpecification.pathParam("issueKey", issueKey).body(requestParams.toString()).put("/{issueKey}");
        System.out.println(response.statusCode());
        Assert.assertTrue(response.statusCode()==204);
        System.out.println("Bug updated successfully : "+ issueKey);
    }

    private static void searchDefect(String issueKey) {
    	
        Response response = reqSpecification.pathParam("issueKey", issueKey).get("/{issueKey}");
        System.out.println("Search bug : " + issueKey);
        System.out.println(response.asPrettyString());
        Assert.assertTrue(response.statusCode()==200);
    }

    private static void addAttachment(String issueKey) {
        String addAttachmentEndpoint ="/attachments";
        File attachmentFile = new File(System.getProperty("user.dir") + "\\src\\main\\java\\JiraAutomation\\myfile.txt");
        
        Response response = RestAssured.given().baseUri(BASE_URL).basePath(basePath)
        		.header("Authorization", userAuthToken)
        		.header("Accept", "application/json")
        		.header("X-Atlassian-Token", "no-check")
        		.pathParam("issueKey", issueKey)
                .multiPart(attachmentFile)
        		.post("/{issueKey}"+ addAttachmentEndpoint);
        
        System.out.println(response.statusCode() + "\n\nResponse : \n" + response.asPrettyString());
        Assert.assertTrue(response.statusCode()==200);
        System.out.println("Attachment added successfully for bug : " + issueKey);
    }

    private static void deleteDefect(String issueKey) {
        Response response = reqSpecification.pathParam("issueKey", issueKey).delete("/{issueKey}");
        Assert.assertTrue(response.statusCode()==204);
        System.out.println("Defect deleted successfully :" + issueKey);
    }
}