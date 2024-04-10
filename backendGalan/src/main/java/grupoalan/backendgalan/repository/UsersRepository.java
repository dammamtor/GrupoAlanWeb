package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
