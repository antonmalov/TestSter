package servises;

import assertions.AssertableResponse;
import clients.UserClient;
import com.github.javafaker.Faker;
import models.userDTO.request.AuthDataRequest;
import models.userDTO.request.CreateUserRequest;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static assertions.Conditions.hasStatus;

public class UserService {
    private final UserClient userClient;
    private final Faker faker = new Faker();
    private final AtomicLong counter = new AtomicLong(0);

    public UserService(String baseUri) {
        this.userClient = new UserClient(baseUri);
    }

    public CreateUserRequest generateRandomUserWithoutGames() {
        String uniqueLogin = faker.name().username()
                + "_" + UUID.randomUUID().toString().substring(0, 8)
                + "_" + counter.incrementAndGet();

        return CreateUserRequest.builder()
                .login(uniqueLogin)
                .pass(faker.internet().password())
                .build();
    }

    public CreateUserRequest getAdminUser() {
        return CreateUserRequest.builder()
                .login("admin")
                .pass("admin")
                .build();
    }

    public AssertableResponse register(CreateUserRequest user, String token) {
        return new AssertableResponse(userClient.register(user, token));
    }

    public AssertableResponse auth(CreateUserRequest user) {
        AuthDataRequest authData = new AuthDataRequest(user.getLogin(), user.getPass());
        return new AssertableResponse(userClient.auth(authData));
    }

    public String authAndGetToken(CreateUserRequest user) {
        return auth(user)
                .should(hasStatus(200))
                .asJwt();
    }

    public AssertableResponse deleteUser(String token) {
        return new AssertableResponse(userClient.deleteUser(token));
    }

    public AssertableResponse getUserInfo(String token) {
        return new AssertableResponse(userClient.getUserInfo(token));
    }
}
