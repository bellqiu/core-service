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
		@NamedQuery(name="QueryHtmlByKey", query="select h from HTML as h where name=:key"),
		@NamedQuery(name="countAllHtml", query="select count(h.id) from HTML as h")
	})
public class HTML extends Component{

	/**
	 *
	 */
	private static final long serialVersionUID = -5374332799910505972L;
	@Column
	private String content;
	@Column
	private String name;
	
	public HTML() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
