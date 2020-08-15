package br.com.posts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.posts.models.User;
import br.com.posts.repositories.UsersRepository;

@Service
public class ImplementsUserDetailService  implements UserDetailsService{
	
	@Autowired
	private UsersRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByLogin(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.getAuthorities());
	}
	
}
