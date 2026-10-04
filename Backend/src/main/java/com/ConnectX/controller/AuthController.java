package com.ConnectX.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ConnectX.Services.CustomerUserDetailsService;
import com.ConnectX.Services.UserService;
import com.ConnectX.UserRepository.UserRepository;
import com.ConnectX.config.JwtProvider;
import com.ConnectX.models.User;
import com.ConnectX.request.LoginRequest;
import com.ConnectX.response.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetails;
	
	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception
	{
		User isExist=userRepository.findByEmail(user.getEmail());
		if(isExist!=null)
		{
			throw new Exception("Email already in use with another account");
		}
		
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(newUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail()
				,savedUser.getPassword());
		
		String token=JwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse(token,"Register success");
		return res;
	}
	
	@PostMapping("/signin")
	public AuthResponse siginin(@RequestBody LoginRequest loginRequest)
	{
		Authentication authentication =
				authenticate(loginRequest.getEmail(),loginRequest.getPassword());
		String token=JwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse(token,"Login Success");
		return res;
	}

	private Authentication authenticate(String email, String password) {
		UserDetails userDetails=customerUserDetails.loadUserByUsername(email);
		if(userDetails==null)
		{
			throw new BadCredentialsException ("Invail username");
		}
		if(!passwordEncoder.matches(password,userDetails.getPassword()))
		{
			throw new BadCredentialsException("Invaild Password");
		}
		
		return new 
				UsernamePasswordAuthenticationToken
				(userDetails,null,userDetails.getAuthorities());
	}

}
