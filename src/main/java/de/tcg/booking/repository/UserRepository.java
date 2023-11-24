package de.tcg.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tcg.booking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 
    public Optional<User> findByUsername(String username);
}
