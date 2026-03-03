package models.userDTO.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    @Data
    public static class RegisterData {
        private int id;
        private String login;
        private String pass;
        private List<GameResponse> games;
    }
}
