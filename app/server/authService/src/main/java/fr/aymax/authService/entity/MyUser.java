package fr.aymax.authService.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //This tells Hibernate to make a table out of this class
@Table(name="users")
public class MyUser implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="username")
    private String username;
	@Column(name="password")
    private String password;
    @Column(name="role")	
	private String role;
	@Column(name="enabled")
    private short enabled;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public short getEnabled() {
		return enabled;
	}
	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
}
