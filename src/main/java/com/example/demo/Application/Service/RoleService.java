package com.example.demo.Application.Service;

import com.example.demo.Application.Model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getAllRoles();

    Optional<Role> getRoleById(Long id);

    Role saveRole(Role role);

    void deleteRole(Long id);

    Role createRole(Role role);

    Role updateRole(Role role);

    void blockUser(Long userId);

    void verifyUser(Long userId);
}
