package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonAutoDetect
public class User extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5322585792062205187L;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type = Type.L1;

	@ManyToOne(cascade={CascadeType.ALL},optional=true)
	@JoinColumn(name="billing_address_id", updatable=true)
	private Address billingAddress;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="User_has_Address",
				joinColumns=@JoinColumn(name="User_id"),
				inverseJoinColumns=@JoinColumn(name="Address_id"))
	private List<Address> shippingAddresses = new ArrayList<Address>();
	
	public static enum Type {
		L1, L2, L3,

		S1,

		A1, A2
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Address> getShippingAddresses() {
		return shippingAddresses;
	}

	public void setShippingAddresses(List<Address> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}
}
