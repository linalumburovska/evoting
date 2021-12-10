package com.example.evoting.repository;

import com.example.evoting.model.UserPrivateKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrivateKeyRepository extends CrudRepository<UserPrivateKey, Long> {
    UserPrivateKey findUserPrivateKeyByUserId(Long userId);
}
