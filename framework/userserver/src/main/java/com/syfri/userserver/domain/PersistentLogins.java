package com.syfri.userserver.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "s_persistent_logins")
public class PersistentLogins implements Serializable{
	@Id
	@GenericGenerator(name= "idGenerator",strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;
	@Column(name = "series", length = 64, nullable = false)
	private String series;
	@Column(name = "username", length = 64, nullable = false)
	private String username;
	@Column(name = "token", length = 64, nullable = false)
	private String token;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_used", nullable = false)
	private Date last_used;

	public PersistentLogins() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLast_used() {
		return last_used;
	}

	public void setLast_used(Date last_used) {
		this.last_used = last_used;
	}
}
