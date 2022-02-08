package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	long userId;

	@Column(name="username")
	String username;

	@JsonIgnore				// this prevents the salted password from being shown in JSON representation
	@ToString.Exclude
	@Column(name="encrypted_password")
	String encrypted_password;

	// this annotation makes it so that when a user is serialized from JSON, 
	// that the encrypted_password field will be set
	@JsonSetter("password")
	public void setPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.encrypted_password = encoder.encode(password);
	}

	public void assign(User pUser) {
		// this method copies variables from pUser into this
		// this is used to update the user in the repository
		this.username = pUser.username;
	}
}
