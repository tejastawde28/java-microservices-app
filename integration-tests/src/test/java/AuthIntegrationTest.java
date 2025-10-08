import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class AuthIntegrationTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    // Happy Path
    @Test
    public void shouldReturnOKWithValidToken() {
        /* 3 Steps you need to do irrespective of the type of test:
        * 1. Arrange -> do any set up that this test needs in order to work 100% of the time - includes setting up data the test needs etc.
        * 2. Act -> Code we write that triggers the thing we're testing - calling the login endpoint and getting a response
        * 3. Assert -> Assert that the response has a valid token and also has an OK status
        */
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """; // Arrange

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login") // Act
                .then()
                .statusCode(200) // Assert
                .body("token",notNullValue())
                .extract().response();

        System.out.println("Authorized User Test Passed!");
        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
    }

    // Error Path
    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        String loginPayload = """
                    {
                        "email": "invalid_user@test.com",
                        "password": "wrongpassword_1"
                    }
                """;

        given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login") // Act
                .then()
                .statusCode(401);

        System.out.println("Unauthorized User Test Passed!");
    }

}
