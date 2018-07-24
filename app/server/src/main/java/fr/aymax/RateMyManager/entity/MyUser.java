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
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
	
	//Association profile
	//Cascade ALL, meme pour la suppression : supprime user => supprime son profil
    @OneToMany(fetch=FetchType.EAGER, mappedBy="myUser", cascade=CascadeType.ALL)
    private List<Profile> profiles;
	
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	//Association ManagedBy
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable (	
		name="ratem_managed_by", 
		joinColumns=@JoinColumn(name="mb_manager"),
		inverseJoinColumns=@JoinColumn(name="mb_consultant")
	)
	private List<MyUser> consultants;
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable (	
		name="ratem_managed_by", 
		joinColumns=@JoinColumn(name="mb_consultant"),
		inverseJoinColumns=@JoinColumn(name="mb_manager")
	)
	private List<MyUser> managers;
	
	public List<MyUser> getConsultants() {
		return consultants;
	}
	public void setConsultants(List<MyUser> consultants) {
		this.consultants = consultants;
	}
	public List<MyUser> getManagers() {
		return managers;
	}
	public void setManagers(List<MyUser> managers) {
		this.managers = managers;
	}
	
	//Association Feedback
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable (	
		name="ratem_feedback", 
		joinColumns=@JoinColumn(name="f_manager"),
		inverseJoinColumns=@JoinColumn(name="f_consultant")
	)
	private List<MyUser> feedbackConsultants;
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable (	
		name="ratem_feedback", 
		joinColumns=@JoinColumn(name="f_consultant"),
		inverseJoinColumns=@JoinColumn(name="f_manager")
	)
	private List<MyUser> feedbackManagers;
	
	public List<MyUser> getFeedbackConsultants() {
		return feedbackConsultants;
	}
	public void setFeedbackConsultants(List<MyUser> feedbackConsultants) {
		this.feedbackConsultants = feedbackConsultants;
	}
	public List<MyUser> getFeedbackManagers() {
		return feedbackManagers;
	}
	public void setFeedbackManagers(List<MyUser> feedbackManagers) {
		this.feedbackManagers = feedbackManagers;
	}
}
