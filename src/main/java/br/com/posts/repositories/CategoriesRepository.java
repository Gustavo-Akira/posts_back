package br.com.posts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.posts.models.Category;

public interface CategoriesRepository  extends JpaRepository<Category, Long>{

}
