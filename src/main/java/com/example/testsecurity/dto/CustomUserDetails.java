package com.example.testsecurity.dto;

import com.example.testsecurity.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Securtiy에서 User데이터를 사용하기 위한... 메소드??
 * 구현만 해두면 UserDetailsService를 통해서 자동으로 가져다 쓰는듯?
 *
 * UserDetails
 * Spring Security에서 사용자의 정보를 담는 인터페이스.
 * Spring Security에서 사용자의 정보를 불러오기 위해서 구현해야 하는 인터페이스 이다.
 *
 */
public class CustomUserDetails implements UserDetails{

    /**
     * CustomUserDetailsService 클래스에서 'new CustomUserDetails(userData)' 로 사용하기 위해서
     * 생성자를 생성해 주어야 한다.
     */
	private UserEntity userEntity;

	public CustomUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
    /**
     * CustomUserDetailsService 클래스에서 'new CustomUserDetails(userData)' 로 사용하기 위해서
     * 생성자를 생성해 주어야 한다.
     */


	/**
	 * 계정 권한 목록 return
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collection = new ArrayList<>();

		collection.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {

				return userEntity.getRole();
			}
		});

		return collection;
	}

	/**
	 * 계정 비밀번호 return
	 */
	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	/**
	 * 계정의 고유값 return (?)
	 * ( ex : DB PK값, 중복이 없는 이메일 값 )
	 */
	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}
	// 여기까지는 필수.



	/**
	 * 여기부터는 사용자ID가 만료/잠김/사용여부 등 체크 하는 메소드.
	 * 구현하려면 DB에 해당 컬럼이 있어야 한다.
	 *
	 * 계정의 만료 여부 return
	 * true (만료 안됨.)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 계정의 잠김 여부 return
	 *
	 * true (잠기지 않음.)
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 비밀번호 만료 여부 return
	 *
	 * * true (만료 안됨.)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 계정의 활성화 여부 return
	 *
	 * true (활성화 됨.)
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
