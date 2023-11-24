package de.tcg.booking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tcg.booking.entity.User;
import de.tcg.booking.repository.UserRepository;

@Service
public class UserService 
{
	@Autowired
	private UserRepository m_userRepository;
	
	public Optional<User> findUserByUsername(String username)
	{
		return m_userRepository.findByUsername(username);
	}

}
