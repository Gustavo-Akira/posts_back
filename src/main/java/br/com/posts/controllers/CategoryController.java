package br.com.posts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.posts.models.Category;
import br.com.posts.repositories.CategoriesRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoriesRepository categoriesRepository;
	@GetMapping("/")
	public ResponseEntity<List<Category>> getCategories(){
		return ResponseEntity.ok(categoriesRepository.findAll());
	}
}
