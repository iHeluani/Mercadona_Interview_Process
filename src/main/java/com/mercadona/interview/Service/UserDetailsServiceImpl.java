package com.mercadona.interview.Service;

import com.mercadona.interview.Model.User;
import com.mercadona.interview.Model.UserDetailsImpl;
import com.mercadona.interview.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return new UserDetailsImpl(user);
    }

    public void save(User user) {
        user.setEnabled(true); // Asegúrate de que esté habilitado
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user); // Guarda el nuevo usuario en la base de datos
    }
}