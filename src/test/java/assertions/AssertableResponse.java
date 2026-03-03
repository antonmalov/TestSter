package assertions;

import io.restassured.response.ValidatableResponse;

import java.util.List;


public class AssertableResponse {
    private final ValidatableResponse response;

    public AssertableResponse(ValidatableResponse response) {
        this.response = response;
    }

    public String asJwt() {
        return response.extract().jsonPath().getString("token");
    }

    public AssertableResponse should(Condition condition) {
        condition.check(response);
        return this;
    }

    public <T> T as(Class<T> tClass) {
        return response.extract().as(tClass);
    }

    public <T> List<T> asList(Class<T> tClass) {
        return response.extract().jsonPath().getList("", tClass);
    }
}

