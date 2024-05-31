package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByUsername(String nombreUsuario);

    Optional<User> findByUsernameAndAccountType(String currentUser, User.AccountType accountType);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndAccountType(String admin, User.AccountType accountType);
}
