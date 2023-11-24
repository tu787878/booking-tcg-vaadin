package de.tcg.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tcg.booking.entity.ParentCategory;
import de.tcg.booking.repository.ParentCategoryRepository;

@Service
public class ParentCategoryService 
{
	@Autowired
	private ParentCategoryRepository m_parentCategoryRepository;

	public List<ParentCategory> findAll() {
		return m_parentCategoryRepository.findAll();
	}
	

}
