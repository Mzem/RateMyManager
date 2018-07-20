package fr.aymax.RateMyManager.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

@Entity //This tells Hibernate to make a table out of this class
@Table(name="ratem_user")
public class MyUser implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="u_email")
    private String username;
	@Column(name="u_password")
    private String password;
    @Column(name="u_first_name")	
	private String firstName;
    @Column(name="u_last_name")	
	private String lastName;
	@Column(name="u_enabled")
    private short enabled;
	@Column(name="u_created_at")
    private String createdAt;
	@Column(name="u_updated_at")
    private String updatedAt;
    
    //Cascade ALL, meme pour la suppression : supprime user => supprime son profil
    @OneToMany(fetch=FetchType.EAGER, mappedBy="myUser", cascade=CascadeType.ALL)
    private List<Profile> profiles;

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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public short getEnabled() {
		return enabled;
	}
	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
}
