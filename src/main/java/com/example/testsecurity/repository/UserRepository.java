package com.example.testsecurity.repository;

import com.example.testsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository<Entity클래스, Entity의 @Id변수의 type>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{				

	boolean existsByUsername(String username);                      // JPA 커스텀 메소드.(existsBy*). (참고: @Query 를 이용해서 쿼리를 사용자 정의로 지정해줄수도 있는듯.)

	UserEntity findByUsername(String username);						// JPA 커스텀 메소드. (findBy*)
	
	

}
