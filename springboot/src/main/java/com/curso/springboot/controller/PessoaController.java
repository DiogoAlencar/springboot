package com.curso.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.springboot.model.Pessoa;
import com.curso.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(value = "/cadastropessoa", method = RequestMethod.GET)
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaObj", new Pessoa());
		return modelAndView;
	}
	
	@RequestMapping(value = "*/salvarpessoa", method = RequestMethod.POST)
	public ModelAndView salvar(Pessoa pessoa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa"); 
		try {
			
			pessoaRepository.save(pessoa); 
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll(); 
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaObj", new Pessoa()); 
			return modelAndView;
			
		} catch (Exception e) {
			modelAndView.addObject("pessoaObj", new Pessoa()); 
			return modelAndView;
		}
		
	}
	
	@RequestMapping(value = "/listapessoas", method = RequestMethod.GET)
	public ModelAndView listaPessoas() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaObj", new Pessoa());
		return modelAndView;
	}
	
	@RequestMapping(value = "/editarpessoa/{idpessoa}", method = RequestMethod.GET)
	public ModelAndView editarPessoas(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		try {
			Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
			modelAndView.addObject("pessoaObj", pessoa.get());
			return modelAndView;
		} catch (Exception e) {
			// TODO: handle exception
			modelAndView.addObject("pessoaObj", new Pessoa());
			return modelAndView;
		}
	}
	
	
	@RequestMapping(value = "/excluirpessoa/{idpessoa}", method = RequestMethod.GET)
	public ModelAndView excluirPessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		try {
			pessoaRepository.deleteById(idpessoa);
			modelAndView.addObject("pessoas", pessoaRepository.findAll());
			modelAndView.addObject("pessoaObj", new Pessoa());
			return modelAndView;
		} catch (Exception e) {
			// TODO: handle exception
			modelAndView.addObject("pessoas", pessoaRepository.findAll());
			modelAndView.addObject("pessoaObj", new Pessoa());
			return modelAndView;
		}
	}
	
}
