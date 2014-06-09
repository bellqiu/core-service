package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@NamedQuery(name="QueryBloggerByName", query="select b from Blogger as b where name=:name "),
	@NamedQuery(name="countAllBlogger", query="select count(b.id) from Blogger as b where b.status = 'ACTIVE' ")
})
public class Blogger extends Component{

	private static final long serialVersionUID = -1698610860522003320L;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="priority")
	private int priority;
	
	@Column(name="size")
	private int size;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type = Type.PDF;
	
	public static enum Type {
		PDF,
		HTML,
		JSP,
		TXT
	}
	
	public Blogger() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
