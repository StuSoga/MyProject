package com.zero.entity;

import com.zero.dao.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zero on 16/3/1.
 */
@Entity
@Table(name="member")
public class Member extends BaseEntity {

	@Column(name = "code")
	private String code;//编码

	@Column(name="name")
	private String name;//昵称

	@Column(name="username")
	private String username;//用户名

	@Column(name="password")
	private String password;//密码

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;//创建时间

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
