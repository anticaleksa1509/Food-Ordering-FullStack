package raf.rs.WebProject2025.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.rs.WebProject2025.entities.ErrorMessage;

import java.util.List;

public interface ErrorRepo extends JpaRepository<ErrorMessage,Long> {



    List<ErrorMessage> findByIdUser(Integer idUser);
}
