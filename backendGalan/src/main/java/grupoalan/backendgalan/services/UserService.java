package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired // Inyecta automáticamente una instancia de UserRepository.
    private UserRepository userRepository;

    // Método para registrar un nuevo usuario.
    public User registerUser(User user) {
        // Si la cuenta es profesional, no habilitarla inmediatamente.
        user.setEnabled(!user.getAccountType().equals("professional"));
        return userRepository.save(user);
    }

    // Método para encontrar un usuario por su correo electrónico.
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Método para habilitar un usuario por su ID.
    public User enableUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        return userRepository.save(user);
    }
}
