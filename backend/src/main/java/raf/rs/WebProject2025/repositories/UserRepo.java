package raf.rs.WebProject2025.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.rs.WebProject2025.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email,String password);

}
