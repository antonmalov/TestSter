package base;

import extentions.UserCleanUpExtension;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import servises.GameService;
import servises.UserService;
import steps.UserSteps;

@Data
public class BaseTest {
    protected static final String BASE_URI = "http://85.192.34.140:8080";
    protected static UserService userService;
    protected static GameService gameService;
    protected static String adminToken;

    // Нестатические поля
    protected UserCleanUpExtension cleanUp;
    protected UserSteps userSteps;

    private static final Object lock = new Object();
    private static boolean initialized = false;

    @BeforeAll
    public static void beforeAll() {
        synchronized (lock) {
            if (initialized) {
                return;
            }
            RestAssured.baseURI = BASE_URI;
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

            userService = new UserService(BASE_URI);
            gameService = new GameService(BASE_URI);
            adminToken = userService.authAndGetToken(userService.getAdminUser());
            initialized = true;
        }
    }

    @BeforeEach
    public void setup() {
        System.out.println("Запуск теста в потоке: " + Thread.currentThread().getName());
        cleanUp = new UserCleanUpExtension(userService);
        userSteps = new UserSteps(userService, cleanUp, adminToken);
    }

    @AfterEach
    public void tearDown() {
        cleanUp.afterEach(null);
    }

    protected static String getAdminToken() {
        return adminToken;
    }
}