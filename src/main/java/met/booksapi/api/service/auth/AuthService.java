package met.booksapi.api.service.auth;

import met.booksapi.api.dto.AuthResponseDTO;
import met.booksapi.api.dto.LoginRequestDTO;
import met.booksapi.api.dto.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO register(RegisterRequestDTO request);
}
