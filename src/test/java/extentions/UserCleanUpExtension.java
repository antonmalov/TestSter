package extentions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import servises.UserService;

import java.util.ArrayList;
import java.util.List;

import static assertions.Conditions.hasStatus;

public class UserCleanUpExtension implements AfterEachCallback {
    private final UserService userService;
    private final List<String> userTokens = new ArrayList<>();

    public UserCleanUpExtension(UserService userService) {
        this.userService = userService;
    }

    public void addToken(String token) {
        userTokens.add(token);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        for (String token : userTokens) {
            try {
                userService.deleteUser(token).should(hasStatus(200));
                System.out.println("Удалён пользователь с токеном: " + token);
            } catch (Exception e) {
                if (e.getMessage().contains("404")) {
                    System.out.println("Пользователь уже удалён");
                } else {
                    throw e;
                }
            }
        }
        userTokens.clear();
    }
}
