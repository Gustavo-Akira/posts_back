package br.com.posts.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "getPosts"})
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	private String email;

	private String senha;

	private String url_photo;

	private String name;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	private String city;

	private String state;

	private String country;
	
	@JsonIgnore()
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
	private List<Posts> posts;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(name = "usuarios_role",
	uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id","role_id"}),
	joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName ="id",table = "usuario",foreignKey = @ForeignKey(name="usuario_fk", value = ConstraintMode.CONSTRAINT)), 
	inverseJoinColumns =  @JoinColumn(name = "role_id", unique = false,referencedColumnName = "id",updatable = false,table = "role",foreignKey = @ForeignKey(name="role_fk",value = ConstraintMode.CONSTRAINT))
	)
	private List<Role> roles;
	
	@ManyToMany
	@JoinTable(name = "usuario_relationship",
	joinColumns = @JoinColumn(referencedColumnName = "id", table = "usuario",foreignKey =@ForeignKey(name = "usuario_from_fk", value = ConstraintMode.CONSTRAINT)),
	inverseJoinColumns = @JoinColumn(referencedColumnName = "id", table = "usuario",foreignKey =@ForeignKey(name = "usuario_to_fk",value = ConstraintMode.CONSTRAINT) ))
	private List<User> user_to;
 
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String getPassword() {
		return this.senha;
	}
	public void setSenha(String password) {
		this.senha =password;
	}
	public String getSenha() {
		return senha;
	}
	public String getUrl_photo() {
		return url_photo;
	}

	public void setUrl_photo(String url_photo) {
		this.url_photo = url_photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Posts> getPosts() {
		return posts;
	}

	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}
	

	public List<User> getUser() {
		return user_to;
	}

	public void setUser(List<User> user) {
		this.user_to = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (Id ^ (Id >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Id != other.Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", email=" + email + ", password=" + senha + ", url_photo=" + url_photo + ", name="
				+ name + ", birthdate=" + birthdate + ", city=" + city + ", state=" + state + ", country=" + country
				+ "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
