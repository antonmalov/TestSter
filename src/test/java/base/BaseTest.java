package base;

import extentions.UserCleanUpExtension;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import servises.GameService;
import servises.UserService;
import steps.UserSteps;

@Data
@AllArgsConstructor
public class BaseTest {
    protected static final String BASE_URI = "http://85.192.34.140:8080";
    protected static UserService userService;
    protected static GameService gameService;
    protected static UserCleanUpExtension cleanUp;
    protected static String adminToken;
    protected static UserSteps userSteps;

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        userService = new UserService(BASE_URI);
        gameService = new GameService(BASE_URI);
        cleanUp = new UserCleanUpExtension(userService);

        adminToken = userService.authAndGetToken(userService.getAdminUser());
        userSteps = new UserSteps(userService, cleanUp, adminToken);
    }

    protected static String getAdminToken() {
        return adminToken;
    }

    @BeforeEach
    public void setup() {
        userSteps = new UserSteps(userService, cleanUp, adminToken);
    }
}

