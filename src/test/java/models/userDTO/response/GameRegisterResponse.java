package models.userDTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameRegisterResponse {
    @JsonProperty("register_data")
    private GameResponse registerData;
    private Info info;
}
