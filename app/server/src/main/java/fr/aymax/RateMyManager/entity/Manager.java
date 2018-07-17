package fr.aymax.RateMyManager.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //This tells Hibernate to make a table out of this class
@Table(name="managers")
public class Manager implements Serializable 
{
	private static final long serialVersionMID = 1L;
    @Id
    @Column(name="username")
    private String username;
	@Column(name="first_name")
    private String firstName;
    @Column(name="last_name")	
	private String lastName;
	@Column(name="description")
    private String description;
	
	public Manager(){}
	
	public Manager(String username, String firstName, String lastName, String description) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public String toString(){  
        return "Manager{" +
        "username=" + username + 
        ", firstName='" + firstName + '\'' + 
        ", lastName='" + lastName + '\'' +
        ", description='" + description + "\'}";
    }
}
