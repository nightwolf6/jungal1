package com.example.demo.Application.Repository;

import com.example.demo.Application.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    // Puedes agregar métodos personalizados según tus necesidades de consulta
    User findByVerificationToken(String token);
    Optional<User> findByImageIdentifier(String imageIdentifier);
}
