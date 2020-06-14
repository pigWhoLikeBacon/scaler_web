package com.scaler.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plan")
public class Plan extends Base {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column
	private boolean isActive;
	
	@Column
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
//	@JsonIgnore
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "day_plan", joinColumns = @JoinColumn(name = "plan_id"),
//	inverseJoinColumns = @JoinColumn(name = "day_id"))
//	@Fetch(FetchMode.SUBSELECT)
//	private Collection<Day> days = new ArrayList<Day>();
}
