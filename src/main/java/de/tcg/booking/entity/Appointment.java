package de.tcg.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name="reservation_id", nullable=false)
    private Reservation reservation;
	
    @OneToOne
    private TimeSection day;
    
    @OneToOne
    private Employee emnployee;
    
    @OneToOne
    private Service service;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public TimeSection getDay() {
		return day;
	}

	public void setDay(TimeSection day) {
		this.day = day;
	}

	public Employee getEmnployee() {
		return emnployee;
	}

	public void setEmnployee(Employee emnployee) {
		this.emnployee = emnployee;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
    
}
