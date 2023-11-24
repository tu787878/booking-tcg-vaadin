package de.tcg.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tcg.booking.entity.Category;
import de.tcg.booking.repository.CategoryRepository;

@Service
public class CategoryService 
{
	@Autowired
	private CategoryRepository m_categoryRepository;

	public List<Category> findAll() {
		return m_categoryRepository.findAll();
	}
	
}
