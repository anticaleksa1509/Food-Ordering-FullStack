package raf.rs.WebProject2025.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.rs.WebProject2025.entities.ErrorLog;

import java.util.List;

public interface ErrorLogRepo extends JpaRepository<ErrorLog,Long> {
    List<ErrorLog> findByUserId(Long userId);


}
