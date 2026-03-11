package servises;

import assertions.AssertableResponse;
import clients.GameClient;
import com.github.javafaker.Faker;
import models.userDTO.request.DlcRequest;
import models.userDTO.request.GameRequest;
import models.userDTO.request.RequirementsRequest;
import models.userDTO.request.SimilarDlcRequest;
import models.userDTO.response.GameRegisterResponse;
import models.userDTO.response.GameResponse;
import org.assertj.core.api.Assertions;
import utils.FakerProvider;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatus;
import static org.assertj.core.api.Assertions.assertThat;

public class GameService {
    private static final ThreadLocal<Faker> faker = ThreadLocal.withInitial(Faker::new);
    private final GameClient gameClient;

    public GameService(String baseUri) {
        this.gameClient = new GameClient(baseUri);
    }

    private static void assertDlcEquals(DlcRequest expected, GameResponse.DlcResponse actual) {
        Assertions.assertThat(actual)
                .as("Проверка DLC: " + expected.getDlcName())
                .usingRecursiveComparison()
                .ignoringFields("similarDlc")
                .isEqualTo(expected);
    }

    public GameRequest generateRandomGame() {
        Faker f = FakerProvider.getFaker();
        return GameRequest.builder()
                .company(f.company().name())
                .title(f.book().title())
                .description(f.lorem().paragraph())
                .genre(f.book().genre())
                .price(f.number().randomDouble(2, 10, 100))
                .isFree(false)
                .publishDate(Instant.now().toString())
                .rating(f.number().randomDigit())
                .requiredAge(f.bool().bool())
                .requirements(generateRandomRequirements())
                .tags(generateRandomTags())
                .dlcs(new ArrayList<>()) // без DLC по умолчанию
                .build();
    }

    public DlcRequest generateRandomDlc() {
        Faker f = FakerProvider.getFaker();
        return DlcRequest.builder()
                .dlcName(f.commerce().productName())
                .description(f.lorem().sentence())
                .isDlcFree(f.bool().bool())
                .price(Math.round(f.number().randomDouble(2, 10, 100)))
                .rating(Math.round(f.number().randomDouble(2, 1, 10)))
                .similarDlc(generateRandomSimilarDlc())
                .build();
    }

    public SimilarDlcRequest generateRandomSimilarDlc() {
        Faker f = FakerProvider.getFaker();
        if (f.bool().bool()) {
            return SimilarDlcRequest.builder()
                    .dlcNameFromAnotherGame(f.commerce().productName())
                    .isFree(f.bool().bool())
                    .build();
        }
        return null;
    }

    public RequirementsRequest generateRandomRequirements() {
        Faker f = FakerProvider.getFaker();
        return RequirementsRequest.builder()
                .osName(f.options().option("Windows 10", "Windows 11", "macOS", "Linux"))
                .ramGb(f.number().numberBetween(4, 32))
                .hardDrive(f.number().numberBetween(10, 100))
                .videoCard(f.options().option("GTX 1060", "RTX 2060", "Intel UHD", "M1"))
                .build();
    }

    private List<String> generateRandomTags() {
        Faker f = FakerProvider.getFaker();
        List<String> tags = new ArrayList<>();
        int tagCount = f.number().numberBetween(1, 5);
        for (int i = 0; i < tagCount; i++) {
            tags.add(f.options().option("RPG", "Action", "Adventure", "Strategy", "Simulation"));
        }
        return tags;
    }

    public AssertableResponse addGame(GameRequest game, String token) {
        return new AssertableResponse(gameClient.addGame(game, token));
    }

    public AssertableResponse getUserGames(String token) {
        return new AssertableResponse(gameClient.getUserGames(token));
    }

    public AssertableResponse addDlcToGame(int gameId, List<DlcRequest> dlcs, String token) {
        return new AssertableResponse(gameClient.addDlcToGame(gameId, dlcs, token));
    }

    public AssertableResponse deleteGame(int gameId, String token) {
        return new AssertableResponse(gameClient.deleteGame(gameId, token));
    }

    public GameRegisterResponse addGameSuccess(GameRequest game, String token) {
        return addGame(game, token)
                .should(hasStatus(201))
                .as(GameRegisterResponse.class);
    }

    public List<GameResponse> getUserGamesAsList(String token) {
        return getUserGames(token)
                .should(hasStatus(200))
                .asList(GameResponse.class);
    }

    public void deleteGameSuccess(int gameId, String token) {
        deleteGame(gameId, token)
                .should(hasStatus(200))
                .should(hasMessage("Game successfully deleted"));
    }

    public void assertGameEquals(GameRequest expected, GameResponse actual) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("gameId", "publish_date")
                .isEqualTo(expected);
    }

    public void assertDlcListsEqual(List<DlcRequest> expected, List<GameResponse.DlcResponse> actual) {
        Assertions.assertThat(actual)
                .as("Количество DLC должно совпадать")
                .hasSize(expected.size());

        for (DlcRequest expectedDlc : expected) {
            GameResponse.DlcResponse actualDlc = actual.stream()
                    .filter(d -> d.getDlcName().equals(expectedDlc.getDlcName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError(
                            "DLC с именем '" + expectedDlc.getDlcName() + "' не найден в ответе"
                    ));

            assertDlcEquals(expectedDlc, actualDlc);
        }
    }

    public List<DlcRequest> createDlcRequests(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateRandomDlc())
                .collect(Collectors.toList());
    }

    public GameResponse findGameById(List<GameResponse> games, int gameId) {
        return games.stream()
                .filter(g -> g.getGameId() == gameId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Игра не найдена: " + gameId));
    }

    public GameResponse findGameById(String token, int gameId) {
        List<GameResponse> games = getUserGamesAsList(token);
        return findGameById(games, gameId);
    }
}
