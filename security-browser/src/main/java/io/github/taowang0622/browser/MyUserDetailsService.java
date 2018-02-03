package io.github.taowang0622.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//This service will be used by filters to authenticate passed username and password!!
@Component
public class MyUserDetailsService implements UserDetailsService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //This class is on Service Layer, so it autowires daos from DAO Layer to determine if username exists
    //@Autowired
    //private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("Login Username: " + s);
        //userDao.search(s)===>if not found, return the exception "UsernameNotFoundException"!!!

        //User in org.springframework.security.core.userdetails implements the interface UserDetails!!

        //hardcoded here, but in production they should be read from DATABASE!!
//        return new User(s, "1234", AuthorityUtils.commaSeparatedStringToAuthorityList("admin, manager"));

        //In production, this line should be done during user sign up!!!!!
        String password = passwordEncoder.encode("1234");

        logger.info("Password: " + password);

        //specifying isEnabled, isAccountNonExpired, isCredentialsNonExpired, isAccountNonLocked
        return new User(s, password, true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin, manager"));
    }
}
