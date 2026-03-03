package steps;

import extentions.UserCleanUpExtension;
import models.userDTO.request.CreateUserRequest;
import servises.UserService;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatus;

public class UserSteps {
    private final UserService userService;
    private final UserCleanUpExtension cleanUp;
    private final String adminToken;

    public UserSteps(UserService userService, UserCleanUpExtension cleanUp, String adminToken) {
        this.userService = userService;
        this.cleanUp = cleanUp;
        this.adminToken = adminToken;
    }

    public CreatedUser createRandomUser() {
        CreateUserRequest newUser = userService.generateRandomUserWithoutGames();

        userService.register(newUser, adminToken)
                .should(hasStatus(201))
                .should(hasMessage("User created"));

        String userToken = userService.authAndGetToken(newUser);
        cleanUp.addToken(userToken);

        return new CreatedUser(newUser, userToken);
    }

    public static class CreatedUser {
        private final CreateUserRequest request;
        private final String token;

        public CreatedUser(CreateUserRequest request, String token) {
            this.request = request;
            this.token = token;
        }

        public CreateUserRequest getRequest() {
            return request;
        }

        public String getToken() {
            return token;
        }
    }
}