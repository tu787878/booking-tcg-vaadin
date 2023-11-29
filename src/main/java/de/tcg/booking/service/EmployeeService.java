package de.tcg.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tcg.booking.entity.Employee;
import de.tcg.booking.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository m_employeeRepository;

	public List<Employee> findAll() {
		return m_employeeRepository.findAll();
	}

	public Employee save(Employee employee) {
		return m_employeeRepository.save(employee);
	}

}
