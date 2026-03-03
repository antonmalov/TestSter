package tests;

import base.BaseTest;
import models.userDTO.request.CreateUserRequest;
import models.userDTO.response.UserResponse;
import org.junit.jupiter.api.Test;
import steps.UserSteps;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatus;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTests extends BaseTest {

    @Test
    public void successRegisterUserTest() {
        UserSteps.CreatedUser user = userSteps.createRandomUser();
        UserResponse.RegisterData userData = userService.getUserInfo(user.getToken())
                .should(hasStatus(200))
                .as(UserResponse.RegisterData.class);

        assertThat(userData)
                .usingRecursiveComparison()
                .ignoringFields("id", "games")
                .isEqualTo(user.getRequest());
    }

    @Test
    public void userAlreadyExistsTest() {
        CreateUserRequest createUserRequest = userService.generateRandomUserWithoutGames();
        userService.register(createUserRequest, getAdminToken());

        userService.register(createUserRequest, getAdminToken())
                .should(hasStatus(400))
                .should(hasMessage("Login already exist"));
    }
}
