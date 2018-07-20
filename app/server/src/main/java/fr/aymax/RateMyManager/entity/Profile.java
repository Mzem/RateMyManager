package fr.aymax.RateMyManager.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="ratem_profile")
public class Profile implements Serializable 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="p_id")
    private Integer id;
	@Column(name="p_role")
    private String role;
    
    //Specifier tous les types de cascade sauf REMOVE, car suppr profil ne doit pas impliquer suppr user
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="p_user")	
	private MyUser myUser;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public MyUser getMyUser() {
		return myUser;
	}
	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
