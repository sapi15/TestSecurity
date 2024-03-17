package com.example.testsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testsecurity.entity.UserEntity;

/**
 * JpaRepository<Entity클래스, Entity의 @Id변수의 type>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{				

//	boolean existsByUsername(String username);

//	UserEntity findByUsername(String username);						// JPA 커스텀 메소드. (findBy)
	
	

}
