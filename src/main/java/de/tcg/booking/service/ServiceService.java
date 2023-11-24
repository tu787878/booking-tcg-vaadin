package de.tcg.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tcg.booking.repository.ServiceRepository;

@Service
public class ServiceService 
{
	@Autowired
	private ServiceRepository m_serviceRepository;

	public List<de.tcg.booking.entity.Service> findAll() {
		return m_serviceRepository.findAll();
	}
	
}
