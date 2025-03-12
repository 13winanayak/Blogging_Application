package com.app.blogging.controller;

import com.app.blogging.config.JwtProvider;
import com.app.blogging.exception.UserException;
import com.app.blogging.model.User;
import com.app.blogging.repository.UserRepository;
import com.app.blogging.request.LoginRequest;
import com.app.blogging.response.UserResponse;
import com.app.blogging.service.CustomeUserServiceImplementation;
import com.app.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;



@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomeUserServiceImplementation customUserDetails;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> createUserHandler(
            @RequestBody User user) throws UserException {

        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getUsername();


        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist!=null) {

            throw new UserException("Email Is Already Used With Another Account");
        }

        // Create new user
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setUsername(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(createdUser);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        UserResponse userResponse = new UserResponse();
        userResponse.setJwt(token);
        userResponse.setMessage("Register Success");

        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signing(@RequestBody LoginRequest loginRequest) throws UserException {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(username + " ----- " + password);

        Authentication authentication = authenticate(username, password);

        User user=userService.findUserByEmail(username);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);



        UserResponse authResponse = new UserResponse();

        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("sign in userDetails - " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
