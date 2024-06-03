package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.exceptions.UserAlreadyEnabledException;
import grupoalan.backendgalan.exceptions.UserManagementException;
import grupoalan.backendgalan.exceptions.UserNotFoundException;
import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.request.UsuarioAdminRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioRequest;
import grupoalan.backendgalan.services.AdminService;
import grupoalan.backendgalan.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/grupo-alan/admin")
public class AdminController {
    static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/create-default-admin")
    public ResponseEntity<String> createDefaultAdmin() {
        try {
            adminService.createDefaultAdminUser();
            return ResponseEntity.ok("Default admin user created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create default admin user: " + e.getMessage());
        }
    }

    @GetMapping("/session")
    public String getSession(HttpSession session) {
        // Verificar si el objeto HttpSession es nulo
        if (session == null) {
            return "La sesión es nula";
        } else {
            String sessionId = session.getId();
            String currentUser = (String) session.getAttribute("currentUser");
            String message = "Id de sesión: " + sessionId + (currentUser != null ? ", Usuario actual: " + currentUser : "");
            logger.info(message);
            return "La sesión no es nula";

        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(
            @RequestBody UsuarioAdminRegisterRequest userRequest,
            HttpSession session) {
        try {
            // Accede a la sesión del usuario
            String currentUser = (String) session.getAttribute("currentUser");

            if (currentUser == null) {
                // El usuario no está autenticado
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
            }

            // Registra el usuario administrador
            adminService.registerAdminUser(userRequest, currentUser);

            return ResponseEntity.ok("Usuario administrador registrado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        try {
            // Obtener el usuario actual antes de invalidar la sesión
            String currentUser = (String) session.getAttribute("currentUser");

            // Invalidar la sesión actual
            if (session != null) {
                session.invalidate();
            }

            // Registrar en el log que la sesión del usuario actual se ha cerrado
            if (currentUser != null) {
                logger.info("La sesión del usuario {} se ha cerrado", currentUser);
            } else {
                logger.info("Sesión cerrada para un usuario no identificado");
            }

            return ResponseEntity.ok("Sesión cerrada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cerrar la sesión: " + e.getMessage());
        }
    }

    @PostMapping("/manage-user/{userId}")
    public ResponseEntity<String> manageUser(
            @PathVariable Long userId,
            @RequestParam boolean enable) {
        try {
            adminService.manageUser(userId, enable);
            return ResponseEntity.ok("Operación de gestión de usuario realizada exitosamente");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserAlreadyEnabledException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserManagementException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al gestionar el usuario: " + e.getMessage());
        }
    }
}
