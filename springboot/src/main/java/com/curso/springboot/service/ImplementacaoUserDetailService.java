package com.curso.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.curso.springboot.model.Pessoa;
import com.curso.springboot.repository.PessoaRepository;

@Service
public class ImplementacaoUserDetailService implements UserDetailsService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Pessoa pessoa = pessoaRepository.findPessoaByLogin(username);
		
		if (pessoa == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return new User(pessoa.getLogin(), pessoa.getPassword(), pessoa.getAuthorities());
	}

}
