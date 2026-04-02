
@ApplicationScoped
public class AuthService {
    public UserEntity authenticate(String email, String password) {
        UserEntity user = UserEntity.find("email", email).firstResult();
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}