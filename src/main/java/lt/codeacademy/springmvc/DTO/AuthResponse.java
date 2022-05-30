package lt.codeacademy.springmvc.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String authenticationToken;
    private String username;

}
