package com.czxy.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name="t_menu")
public class Menu implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //主键
	@Column(name="menu_id")
	private Integer menu_id;
	
	@Column(name="menu_name")
	private String menu_name;
	
	@Column(name="menu_url")
	private String menu_url;
	
	//建立角色和菜单的关联关系，多个菜单对应多个角色
	@ManyToMany(mappedBy="menu")	//对应role中的menu对象名
	private Set<Role> role = new HashSet<>();

	public Integer getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Menu [menu_id=" + menu_id + ", menu_name=" + menu_name + ", menu_url=" + menu_url + "]";
	}
}
