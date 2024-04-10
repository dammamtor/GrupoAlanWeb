package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.Users;
import grupoalan.backendgalan.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    // Método para encontrar un usuario por su ID
    public Users getUserByID(Long id){
        return usersRepository.findById(id).orElse(null);
    }

    // Método para encontrar todos los usuarios
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    // Método para guardar un nuevo usuario
    public Users saveUser(Users user){
        return usersRepository.save(user);
    }

    // Método para eliminar un usuario por su ID
    public void deleteUserByID(Long id){
        usersRepository.deleteById(id);
    }
}
