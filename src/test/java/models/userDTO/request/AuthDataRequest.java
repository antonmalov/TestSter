package models.userDTO.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDataRequest {
    private String username;
    private String password;
}
