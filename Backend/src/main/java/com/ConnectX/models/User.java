package com.ConnectX.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <--- Add this line
    private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
	
	private List<Integer> followers=new ArrayList<>();
	private List<Integer> following=new ArrayList<>();
	private String gender;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Story> stories = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany
	private List<Post> savedPost=new ArrayList<>();
	
	

//	public User(Integer id, String firstName, String lastName, String email, String password, List<Integer> followers,
//			List<Integer> following, String gender, List<Post> savedPost) {
//		super();
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.followers = followers;
//		this.following = following;
//		this.gender = gender;
//		this.savedPost = savedPost;
//	}
//	public List<Post> getSavedPost() {
//		return savedPost;
//	}
//	public void setSavedPost(List<Post> savedPost) {
//		this.savedPost = savedPost;
//	}
//	public User(Integer id, String firstName, String lastName, String email, String password, List<Integer> followers,
//			List<Integer> following, String gender) {
//		super();
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.followers = followers;
//		this.following = following;
//		this.gender = gender;
//	}
//	public List<Integer> getFollowers() {
//		return followers;
//	}
//	public void setFollowers(List<Integer> followers) {
//		this.followers = followers;
//	}
//	public List<Integer> getFollowing() {
//		return following;
//	}
//	public void setFollowing(List<Integer> following) {
//		this.following = following;
//	}
//	public String getGender() {
//		return gender;
//	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//
//	public User() {
//		// TODO Auto-generated constructor stub
//	}
//	public String getFirstName() {
//		return firstName;
//	}
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//	public String getLastName() {
//		return lastName;
//	}
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}


}