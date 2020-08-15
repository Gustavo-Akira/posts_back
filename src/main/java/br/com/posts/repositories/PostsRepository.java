package br.com.posts.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.posts.models.Posts;


@Repository
@Transactional
public interface PostsRepository extends JpaRepository<Posts, Long> {
	
}
