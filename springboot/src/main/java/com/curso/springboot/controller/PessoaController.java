package com.curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.springboot.model.Pessoa;
import com.curso.springboot.model.Telefone;
import com.curso.springboot.repository.PessoaRepository;
import com.curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(value = "/cadastropessoa", method = RequestMethod.GET)
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaObj", new Pessoa());
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		return modelAndView;
	}
	
	@RequestMapping(value = "/salvarpessoa", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaObj", pessoa);
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
			
		}
		
		try {
			pessoaRepository.save(pessoa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaObj", new Pessoa());
		return modelAndView;		
			
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
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		modelAndView.addObject("pessoaObj", pessoa.get());
		return modelAndView;

	}
	
	@RequestMapping(value = "/telefones/{idpessoa}", method = RequestMethod.GET)
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		modelAndView.addObject("pessoaObj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/excluirpessoa/{idpessoa}", method = RequestMethod.GET)
	public ModelAndView excluirPessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		pessoaRepository.deleteById(idpessoa);
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		modelAndView.addObject("pessoaObj", new Pessoa());
		return modelAndView;
	}
	
	
	
	@PostMapping("/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("buscarnome") String buscarnome) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(buscarnome));
		modelAndView.addObject("pessoaObj", new Pessoa());
		return modelAndView;
	}
	
	@PostMapping("/addtelefones/{idpessoa}")
	public ModelAndView addfone(@PathVariable("idpessoa") Long idpessoa, Telefone telefone) {
		
		Pessoa pessoa = pessoaRepository.findById(idpessoa).get();
		telefone.setPessoa(pessoa);
		
		telefoneRepository.save(telefone);

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaObj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/excluirtelefone/{idtelefone}", method = RequestMethod.GET)
	public ModelAndView excluirTelefone(@PathVariable("idtelefone") Long idtelefone) {
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();

		telefoneRepository.deleteById(idtelefone);

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		modelAndView.addObject("pessoaObj", pessoa);
		return modelAndView;

	}
}
