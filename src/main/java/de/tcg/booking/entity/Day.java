package de.tcg.booking.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "day")
public class Day {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean isWeekDay;
	
	private int dayOfWeek; // start with 0 -> 6
	
	private Date specifiedDate;
	
	private int dayType; //0: salon, 1:employee, 2 : reservation
	
    @OneToMany(mappedBy="day")
    private List<TimeSection> timeSections;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isWeekDay() {
		return isWeekDay;
	}

	public void setWeekDay(boolean isWeekDay) {
		this.isWeekDay = isWeekDay;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Date getSpecifiedDate() {
		return specifiedDate;
	}

	public void setSpecifiedDate(Date specifiedDate) {
		this.specifiedDate = specifiedDate;
	}

	public int getDayType() {
		return dayType;
	}

	public void setDayType(int dayType) {
		this.dayType = dayType;
	}

	public List<TimeSection> getTimeSections() {
		return timeSections;
	}

	public void setTimeSections(List<TimeSection> timeSections) {
		this.timeSections = timeSections;
	}
	
}
