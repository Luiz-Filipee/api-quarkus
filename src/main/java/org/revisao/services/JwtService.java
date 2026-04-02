

@ApplicationScoped
public class JwtService {
    private static final String SECRET_KEY = "mySuperSecretKeyForJwtSigning";
    
    public String generateToken(User user) {
        return Jwt.issuer("https://myapp.com")
                .upn(user.getEmail())
                .groups(new HashSet<>(Arrays.asList("ADMIN", "BARBEARIA", "BARBEIRO")))
                .claim("userId", user.getId())
                .expiresIn(Duration.ofHours(8))
                .signWithSecret(SECRET_KEY);
    }
}