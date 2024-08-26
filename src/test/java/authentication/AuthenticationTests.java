package authentication;

import io.qameta.allure.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static in.reqres.utility.getHeaders.getHeadersForUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Faisal Khatri
 * @since 1/10/2023
 **/
public class AuthenticationTests {

    private static final String base_URL = "https://api.github.com/";
    private static final String token_GIT = "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";
    private static RequestSpecBuilder requestSpecBuilder;
    private static ResponseSpecBuilder responseSpecBuilder;
    private static ResponseSpecification responseSpecification;
    private static RequestSpecification requestSpecification;

    @BeforeClass
    public void setupSpecBuilder() {
        requestSpecBuilder = new RequestSpecBuilder().addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());
        responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200);

        responseSpecification = responseSpecBuilder.build();
        requestSpecification = requestSpecBuilder.build();

    }


    @Test
    @Description("Invalid or Empty token")
    public void testUserApiforgetrequest() {

        Response response = (Response) given().when().get(base_URL + "user").getBody();
        Assert.assertEquals(response.getStatusCode(), 401);

    }

    @Test
    @Description("Invalid token")
    public void testUserApiforgetrequestInvalidToken() {

        given().when().headers(getHeadersForUser()).get(base_URL + "user")
                .then().statusCode(200);

    }

    @Test
    @Description("Validate bio Update")
    public void testUserApiforpatchRequestToUpdateBio() {
        String newBio = "This is Shubh";
        given().when().headers(getHeadersForUser()).body("{\"bio\":\"" + newBio + "\"}").log().all().patch(base_URL + "user")
                .then().log().all().statusCode(200).body("bio", equalTo(newBio));


    }

//"Authorization","Bearer "+token_GIT
}
