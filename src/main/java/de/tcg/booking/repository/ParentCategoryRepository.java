package de.tcg.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tcg.booking.entity.ParentCategory;

public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {

}
