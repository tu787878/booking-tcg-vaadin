package de.tcg.booking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Map<Category, List<de.tcg.booking.entity.Service>> getCatServices() {
		List<Category> categories = m_categoryService.findAll();
		Map<Category, List<de.tcg.booking.entity.Service>> results = new HashMap<>();
		for(Category cat : categories) {
			results.put(cat, m_serviceRepository.findAllByCategory(cat));
		}
		return results;
	}
	
}
