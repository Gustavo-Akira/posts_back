package br.com.posts.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.posts.models.User;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<User, Long>{
	
	@Query("select u from User u where u.email = ?1")
	User findUserByLogin(String login);
	@Modifying
	@Query(nativeQuery = true,value = "INSERT INTO usuarios_role(usuario_id,role_id) VALUES(?2,?1)")
	void grantAuthority(long l,long id);
	@Query("select u.user_to from User u where u.id = ?1")
	List<User> findRelationship(long id);
	@Modifying
	@Query(nativeQuery = true, value="INSERT INTO usuario_relationship(usuario_id, usuario_to_id) VALUES(?1,?2)")
	void doRelationship(long id, long friendid);
}
