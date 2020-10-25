package com.spring.token.repositories;

import com.spring.token.model.Token;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by prkumar on 08/10/2020.
 */
@EnableScan
public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findById(int id);
}
