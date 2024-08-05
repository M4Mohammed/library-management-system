package com.project.librarymanagementsystem.auth;

import com.project.librarymanagementsystem.config.AuthConfig;
import com.project.librarymanagementsystem.exceptions.UserAlreadyExistsException;
import com.project.librarymanagementsystem.patrons.PatronService;
import com.project.librarymanagementsystem.patrons.dto.PatronCreateDto;
import com.project.librarymanagementsystem.patrons.dto.PatronResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthConfig authConfig;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager manager;

    private final JwtHelper helper;

    private final PatronService patronService;


    @PostMapping("/create")
    public ResponseEntity<JwtResponse> createUser(@RequestBody PatronCreateDto patronCreateDto) {
        try {
            PatronResponseDto createdPatron = patronService.addPatron(patronCreateDto);
            UserDetails userDetails = userDetailsService.loadUserByUsername(createdPatron.email());

            logger.debug("User created: {}", createdPatron.email());

            String token = this.helper.generateToken(userDetails);
            JwtResponse jwtResponse = JwtResponse.builder().token(token).build();
            return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            // Handle the exception and return an appropriate response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new JwtResponse("User already exists: " + ex.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse jwtResponse = JwtResponse.builder().token(token).build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        logger.debug("Attempting authentication for user: {}", email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            System.out.println("Started");
            manager.authenticate(authentication);

            System.out.println("Ended");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("Authentication successful for user: {}", email);
        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for user: {}", email);
            throw new BadCredentialsException(" Invalid Username or Password  !!");

        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exceptionHandler(BadCredentialsException ex) {
        logger.error("Authentication error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credentials Invalid!");
    }
}
