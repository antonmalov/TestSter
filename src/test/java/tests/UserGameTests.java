package tests;

import base.BaseTest;
import models.userDTO.request.DlcRequest;
import models.userDTO.request.GameRequest;
import models.userDTO.response.GameRegisterResponse;
import models.userDTO.response.GameResponse;
import org.junit.jupiter.api.Test;
import steps.UserSteps;

import java.util.List;


public class UserGameTests extends BaseTest {

    @Test
    public void userCanAddGameTest() {
        UserSteps.CreatedUser user = userSteps.createRandomUser();
        GameRequest gameRequest = gameService.generateRandomGame();
        GameRegisterResponse registerResponse = gameService.addGameSuccess(gameRequest, user.getToken());
        GameResponse gameResponse = registerResponse.getRegisterData();

        gameService.assertGameEquals(gameRequest, gameResponse);
    }

    @Test
    public void userCanDlcToGameTest() {
        UserSteps.CreatedUser user = userSteps.createRandomUser();
        GameRequest gameRequest = gameService.generateRandomGame();
        GameRegisterResponse registerResponse = gameService.addGameSuccess(gameRequest, user.getToken());
        int gameId = registerResponse.getRegisterData().getGameId();

        List<DlcRequest> dlcs = gameService.createDlcRequests(2);
        gameService.addDlcToGame(gameId, dlcs, user.getToken());

        GameResponse updateGame = gameService.findGameById(user.getToken(), gameId);
        gameService.assertDlcListsEqual(dlcs, updateGame.getDlcs());
    }

    @Test
    public void userCanDeleteGameTest() {
        UserSteps.CreatedUser user = userSteps.createRandomUser();
        GameRequest gameRequest = gameService.generateRandomGame();
        GameRegisterResponse gameResponse = gameService.addGameSuccess(gameRequest, user.getToken());
        gameService.deleteGameSuccess(gameResponse.getRegisterData().getGameId(), user.getToken());
    }
}
