package appsec.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import appsec.dao.paidnrv.UserDAO;
import appsec.dto.paidnrv.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserDAO userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // UUID id = UUID.fromString(username);
            UserModel user = userService.getUserById(username).get();

            if (user != null) {
                List<String> roles = new ArrayList<>();
                roles.add(user.getUserType());
                List<GrantedAuthority> authorities = roles.stream().map((role) -> new SimpleGrantedAuthority("ROLE_"+role))
                        .collect(Collectors.toList());
                // Set<SimpleGrantedAuthority> userRole = new HashSet<>();
                // userRole.add(new SimpleGrantedAuthority(user.getUserType()));

                return new User(user.getUserId().toString(), user.getPassword(), authorities);
                // return new User(user.getId().toString(), user.getPassword(), new
                // ArrayList<>());
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        } catch (Exception ex) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }

}