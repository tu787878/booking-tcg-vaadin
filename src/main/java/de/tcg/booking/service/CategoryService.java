package de.tcg.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tcg.booking.entity.Category;
import de.tcg.booking.entity.ParentCategory;
import de.tcg.booking.repository.CategoryRepository;

@Service
public class CategoryService 
{
	@Autowired
	private CategoryRepository m_categoryRepository;
	
	@Autowired
	private ParentCategoryService m_parentCategoryService;

	public List<Category> findAll() {
		return m_categoryRepository.findAll();
	}
	
	public List<ParentCategory> findAllParents() {
		return m_parentCategoryService.findAll();
	}

	public Category save(Category category) {
		return m_categoryRepository.save(category);
	}

	public void delete(Category category) {
		m_categoryRepository.delete(category);
	}

	public long count() {
		return m_categoryRepository.count();
	}
	
}
