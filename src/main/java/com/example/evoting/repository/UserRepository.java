package com.example.evoting.repository;

import com.example.evoting.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByIsLogged(boolean isLogged);

}