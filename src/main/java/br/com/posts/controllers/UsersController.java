package br.com.posts.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.websocket.server.PathParam;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.posts.controllers.helpers.Helper;
import br.com.posts.models.User;
import br.com.posts.repositories.UsersRepository;

@RestController
@RequestMapping(path = "/users")
public class UsersController {
	
	@Autowired
	private UsersRepository repository;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private Helper helper;
	
	@GetMapping("/")
	public ResponseEntity<Page<User>> index(){
		PageRequest page = PageRequest.of(0, 5, Sort.by("name"));
		Page<User> usuarios = repository.findAll(page);
		return ResponseEntity.ok(usuarios);
	}
	@PostMapping(value= "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> new_record(@ModelAttribute User user, MultipartFile image){
		if (image != null && !image.isEmpty()) {
	        String path = servletContext.getContextPath() + "resources/images/" + image.getOriginalFilename();
	        Helper.saveFile(path, image);
	        user.setUrl_photo(path);
		}
		user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
		User response = repository.save(user);
		repository.grantAuthority(1L,response.getId());
		response = repository.findById(response.getId()).get();
		return ResponseEntity.ok(response);
	}
	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable("id") long id){
		User user = repository.findById(id).get();
		return ResponseEntity.ok(user);
	}
	@PostMapping("/friendship/{id}")
	public ResponseEntity<String> relationship(@PathVariable("id") long friendid){
		long id = helper.getUserActive().getId();
		if(repository.findById(friendid).get() != null) {
			repository.doRelationship(id, friendid);
		}else {
			return ResponseEntity.ok("Error: user with this id doesnt exist");
		}
		return ResponseEntity.ok("Friendship realized with success");
	}
	@DeleteMapping("/friendship/{id}")
	public ResponseEntity<String> destroyRelationship(@PathVariable("id") long friendid){
		long id = helper.getUserActive().getId();
		if(repository.findById(friendid).get() != null) {
			repository.doRelationship(id, friendid);
		}else {
			return ResponseEntity.ok("Error: user with this id doesnt exist");
		}
		return ResponseEntity.ok("Friendship realized with success");
	}
}
