package fr.aymax.RateMyManager.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="ratem_managed_by")
public class ManagedBy implements Serializable 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="mb_id")
    private Integer id;
    @Column(name="mb_consultant")
    private String consultant;
	@Column(name="mb_manager")
    private String manager;
    @Column(name="mb_begin_date")	
	private String beginDate;
	@Column(name="mb_end_date")
    private String endDate;
	
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
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
