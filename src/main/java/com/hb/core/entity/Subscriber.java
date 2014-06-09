package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonAutoDetect
@NamedQueries(value=
{
	@NamedQuery(name="queryAciteveSubscriberByName", query="select s from Subscriber as s where email=:email and s.status='ACTIVE' "),
	@NamedQuery(name="querySubscriberByName", query="select s from Subscriber as s where email=:email "),
	@NamedQuery(name="countAllSubscriber", query="select count(s.id) from Subscriber as s where s.status = 'ACTIVE' ")
})
public class Subscriber extends Component{

	private static final long serialVersionUID = -1698610860522003320L;
	
	@Column(name="email")
	private String email;
	
	@Column(name="priority")
	private int priority = 5;
	
	@Column(name="send_count")
	private int count;
	
	public Subscriber() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

}
