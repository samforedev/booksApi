package met.booksapi.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String getToken(UserDetails user);
    String getUsernameFromToken(String token);
    boolean isTokenValid(String token, UserDetails user);

}
