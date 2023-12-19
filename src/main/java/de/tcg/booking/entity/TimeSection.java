package de.tcg.booking.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_section")
public class TimeSection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean isOff; // work or not

	private LocalDate dateFrom;

	private LocalDate dateTo;

	private LocalTime timeFrom;

	private LocalTime timeTo;

	private DayType type;

	private boolean isRepeat;

	private boolean isAllDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isOff() {
		return isOff;
	}

	public void setOff(boolean isOff) {
		this.isOff = isOff;
	}

	public LocalTime getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(LocalTime timeFrom) {
		this.timeFrom = timeFrom;
	}

	public LocalTime getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(LocalTime timeTo) {
		this.timeTo = timeTo;
	}

	public DayType getType() {
		return type;
	}

	public void setType(DayType type) {
		this.type = type;
	}

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

	public void setAllDay(boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

}
