package com.example.demo.Application.Repository;

import com.example.demo.Application.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Puedes agregar métodos personalizados según tus necesidades de consulta
}
