package de.tcg.booking.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name="parent_category_id", nullable=false)
    private ParentCategory parentCategory;
    
//	@OneToMany(mappedBy="category", fetch = FetchType.EAGER)
//    private Set<Service> services;
	
	private String name;
	
	private String description;
	
	private int position;
	
	private boolean isEnable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParentCategory(ParentCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

//	public Set<Service> getServices() {
//		return services;
//	}
//
//	public void setServices(Set<Service> services) {
//		this.services = services;
//	}

	public ParentCategory getParentCategory() {
		return parentCategory;
	}
	
}
