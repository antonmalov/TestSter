package clients;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.userDTO.request.DlcRequest;
import models.userDTO.request.GameRequest;
import java.util.List;
import static io.restassured.RestAssured.given;

public class GameClient {
    private final String baseUri;

    public GameClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public ValidatableResponse addGame(GameRequest game, String token) {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(game)
                .post("/api/user/games")
                .then();
    }

    public ValidatableResponse addDlcToGame(int gameId, List<DlcRequest> dlcs, String token) {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(dlcs)
                .put("/api/user/games/" + gameId)
                .then();
    }

    public ValidatableResponse getUserGames(String token) {
        return given()
                .baseUri(baseUri)
                .auth().oauth2(token)
                .get("/api/user/games")
                .then();
    }

    public ValidatableResponse deleteGame(int gameId, String token) {
        return given()
                .baseUri(baseUri)
                .auth().oauth2(token)
                .delete("/api/user/games/" + gameId)
                .then();
    }
}