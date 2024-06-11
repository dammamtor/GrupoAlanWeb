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

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateAdmin(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            String token = adminService.authenticateAdmin(usuarioRequest);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(
            @RequestBody UsuarioAdminRegisterRequest userRequest,
            @RequestHeader("Authorization") String token) {
        try {
            String currentUser = adminService.getCurrentUserFromToken(token);

            // Registra el usuario administrador
            adminService.registerAdminUser(userRequest, currentUser);

            return ResponseEntity.ok("Usuario administrador registrado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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