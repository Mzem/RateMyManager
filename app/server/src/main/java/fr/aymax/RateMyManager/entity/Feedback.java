package fr.aymax.RateMyManager.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="ratem_feedback")
public class Feedback implements Serializable 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="f_id")
    private Integer id;
	@Column(name="f_consultant")
    private String consultant;
	@Column(name="f_manager")
    private String manager;
    @Column(name="f_month")	
	private String month;
	@Column(name="f_note")
    private double note;
	@Column(name="f_comment")
    private String comment;
    
    public Feedback() {}
    public Feedback(String consultant, String manager, String month) {
		this.consultant = consultant;
		this.manager = manager;
		this.month = month;
		this.note = 0;
		this.comment="";
	}
    public Feedback(String consultant, String manager, String month, double note) {
		this.consultant = consultant;
		this.manager = manager;
		this.month = month;
		this.note = note;
		this.comment="";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConsultant() {
		return consultant;
	}
	public void seConsultant(String consultant) {
		this.consultant = consultant;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getNote() {
		return note;
	}
	public void setNote(double note) {
		this.note = note;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
