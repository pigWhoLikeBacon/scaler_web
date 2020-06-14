package com.scaler.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "day")
public class Day extends Base {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column
	private String log;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "day", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private Collection<Event> events = new ArrayList<Event>();
	
	@OneToMany(mappedBy = "day", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
    private Collection<DayPlan> DayPlans = new ArrayList<DayPlan>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

	public Collection<DayPlan> getDayPlans() {
		return DayPlans;
	}

	public void setDayPlans(Collection<DayPlan> dayPlans) {
		DayPlans = dayPlans;
	}
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "day_plan", joinColumns = @JoinColumn(name = "day_id"),
//	inverseJoinColumns = @JoinColumn(name = "plan_id"))
//	@Fetch(FetchMode.SUBSELECT)
//	private Collection<Plan> plans = new ArrayList<Plan>();
}
