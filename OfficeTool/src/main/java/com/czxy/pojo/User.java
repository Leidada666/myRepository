package com.czxy.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

/**
 * 用户表
 */
@Entity
@Table(name="t_user")
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//主键
	@Column(name="id")
	private Integer id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="telephone",length=40)
	@NotBlank(message="手机号不能为空")
	@Length(max=11, min=11)
	@Size(max=40)
	private String telephone;
	
	@Column(name="password",length=40)
	@NotBlank(message="密码不能为空")
	@Length(max=11, min=8)
	@Size(max=40)
	private String password;

	//建立用户与角色的关联关系，一个角色对应多个用户
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="role_id")
	private Role role;
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", telephone=" + telephone + ", password=" + password
				+ "]";
	}
}
