package com.ecomart.EcoMartBackEnd.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.ecomart.EcoMartCommon.entity.User;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    /*
    * SELECT entity FROM entity_class alias(optional) WHERE condition
    * */
    @Query("SELECT user FROM User user WHERE user.email = :email")
    public User getUserByEmail(@Param("email") String email);
}
