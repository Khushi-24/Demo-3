package com.example.Demo3.serviceImpl;

import com.example.Demo3.Util.JwtUtil;
import com.example.Demo3.dtos.JwtRequest;
import com.example.Demo3.dtos.JwtResponse;
import com.example.Demo3.entities.User;
import com.example.Demo3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class JwtServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private MessageSource messageSource;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userEmail = jwtRequest.getUserEmail();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userEmail, userPassword);

        UserDetails userDetails = loadUserByUsername(userEmail);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUserEmail(userEmail);
        user.setUserPassword(null);
        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail);

        if (user != null) {
                return new org.springframework.security.core.userdetails.User(
                        user.getUserEmail(),
                        user.getUserPassword(),
                        getAuthority(user)
                );

        } else {
            Locale locale = Locale.getDefault();
            throw new UsernameNotFoundException(messageSource.getMessage("user_not_found.message", null, locale));
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
        return authorities;
    }


    private void authenticate(String userEmail, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, userPassword));
        } catch (DisabledException e) {
            Locale locale = Locale.getDefault();
            throw new Exception(messageSource.getMessage("user_disabled.message", null, locale), e);
        } catch (BadCredentialsException e) {
            Locale locale = Locale.getDefault();
            throw new Exception(messageSource.getMessage("invalid_credentials.message", null, locale), e);
        }
    }

}
