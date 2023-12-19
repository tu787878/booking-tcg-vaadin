package de.tcg.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tcg.booking.entity.Category;
import de.tcg.booking.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

	public List<Service> findAllByCategory(Category cat);

}
