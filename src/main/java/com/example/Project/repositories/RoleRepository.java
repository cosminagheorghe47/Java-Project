package com.example.Project.repositories;

import com.example.Project.model.entities.Role;
import com.example.Project.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {

}
