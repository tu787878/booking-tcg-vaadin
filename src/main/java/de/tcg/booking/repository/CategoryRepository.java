package de.tcg.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tcg.booking.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	 
}
