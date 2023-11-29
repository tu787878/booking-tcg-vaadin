package de.tcg.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tcg.booking.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	 
}
