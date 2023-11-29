package de.tcg.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tcg.booking.entity.Category;
import de.tcg.booking.repository.ServiceRepository;

@Service
public class ServiceService 
{
	@Autowired
	private ServiceRepository m_serviceRepository;
	
	@Autowired
	private CategoryService m_categoryService;

	public List<de.tcg.booking.entity.Service> findAll() {
		return m_serviceRepository.findAll();
	}
	
	public List<Category> findAllCategories(){
		return m_categoryService.findAll();
	}

	public de.tcg.booking.entity.Service save(de.tcg.booking.entity.Service service) {
		return m_serviceRepository.save(service);
	}

	public void delete(de.tcg.booking.entity.Service service) {
		m_serviceRepository.delete(service);
	}

	public long count() {
		return m_serviceRepository.count();
	}
	
}
