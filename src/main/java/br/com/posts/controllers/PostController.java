package br.com.posts.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.posts.models.Posts;
import br.com.posts.models.User;
import br.com.posts.repositories.PostsRepository;
import br.com.posts.repositories.UsersRepository;

@RestController
@RequestMapping("/posts")
public class PostController {
	@Autowired
	private UsersRepository userRepository;
	@Autowired
	private PostsRepository repository;
	
	@Autowired
	private ServletContext servletContext;

	@GetMapping("/")
	public ResponseEntity<Page<Posts>> getAll() {
		PageRequest pages = PageRequest.of(0, 5);
		Page<Posts> posts = repository.findAll(pages);
		return ResponseEntity.ok(posts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Posts> getOne(@PathVariable("id") long id) {
		Posts post = repository.findById(id).get();
		return ResponseEntity.ok(post);
	}

	@PostMapping("/")
	public ResponseEntity<Posts> save(@ModelAttribute Posts post,MultipartFile file) throws Exception{
		User user = userRepository
				.findUserByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		post.setUser(user);
		if (file != null && !file.isEmpty()) {
	        String path = servletContext.getContextPath() + "resources/images/posts/" + file.getOriginalFilename();
	        saveFile(path, file);
	        post.setUrl(path);
	    }else {
	    	throw new IOException("");
	    }
		post = repository.save(post);
		return ResponseEntity.ok(post);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Posts> change(@RequestBody Posts post, @PathVariable("id") long id) {
		User user = getUserActive();
		Posts old = repository.findById(id).get();
		if (user != old.getUser()) {
			return ResponseEntity.status(401).body(post);
		}
		post.setId(id);
		repository.save(post);
		return ResponseEntity.ok(post);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		User users = getUserActive();
		Posts post = repository.getOne(id);
		if (post == null) {
			return ResponseEntity.ok("Dont have a post wiht that id");
		}
		if (users != post.getUser()) {
			return ResponseEntity.status(401).body("You dont have authorization to delete this post");
		}
		return ResponseEntity.ok("Post removed com success");
	}

	private User getUserActive() {
		return userRepository
				.findUserByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	private static void saveFile(String path, MultipartFile file) {

		File saveFile = new File(path);
		try {
			FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
