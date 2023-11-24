package de.tcg.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tcg.booking.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}
