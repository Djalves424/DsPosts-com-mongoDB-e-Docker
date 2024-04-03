package com.djalves.workshopmongodocker.repositories;

import com.djalves.workshopmongodocker.models.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
