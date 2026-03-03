package clients;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.Data;
import models.userDTO.request.AuthDataRequest;
import models.userDTO.request.CreateUserRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;

@Data
public class UserClient {
    private final String baseUri;

    public UserClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public ValidatableResponse auth(AuthDataRequest request) {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(request)
                .post("/api/login")
                .then();
    }

    public ValidatableResponse register(CreateUserRequest request, String token) {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(request)
                .post("/api/signup")
                .then();
    }

    public ValidatableResponse deleteUser(String token) {
        return given()
                .baseUri(baseUri)
                .auth().oauth2(token)
                .delete("/api/user")
                .then();
    }

    public ValidatableResponse getUserInfo(String token) {
        return given()
                .baseUri(baseUri)
                .auth().oauth2(token)
                .get("/api/user")
                .then();
    }

    public ValidatableResponse getAllUsers() {
        return given()
                .baseUri(baseUri)
                .get("/api/users")
                .then();
    }

    public ValidatableResponse updatePass(String newPassword, String token) {
        Map<String, String> password = Map.of("password", newPassword);
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(password)
                .put("/api/user")
                .then();
    }
}
