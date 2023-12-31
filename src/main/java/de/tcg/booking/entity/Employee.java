package de.tcg.booking.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "employee_service", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
	private List<Service> doServices;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TimeSection> workingDays;

	private String name;

	private int age;

	private String colorHex;

	private String textColorHex;

	private String email;

	private boolean isEnable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Service> getDoServices() {
		return doServices;
	}

	public void setDoServices(List<Service> doServices) {
		this.doServices = doServices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getColorHex() {
		return colorHex;
	}

	public void setColorHex(String colorHex) {
		this.colorHex = colorHex;
	}

	public String getTextColorHex() {
		return textColorHex;
	}

	public void setTextColorHex(String textColorHex) {
		this.textColorHex = textColorHex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", doServices=" + doServices + ", workingDays=" + workingDays + ", name=" + name
				+ ", age=" + age + ", colorHex=" + colorHex + ", textColorHex=" + textColorHex + ", email=" + email
				+ ", isEnable=" + isEnable + "]";
	}

	public List<TimeSection> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<TimeSection> workingDays) {
		this.workingDays = workingDays;
	}

}
