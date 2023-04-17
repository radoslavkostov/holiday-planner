package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.entities.UserRoleEntity;
import com.example.holidayplanner.enums.UserRoleEnum;
import com.example.holidayplanner.models.binding.UserEditBindingModel;
import com.example.holidayplanner.models.service.UserServiceModel;
import com.example.holidayplanner.models.view.UserViewModel;
import com.example.holidayplanner.repositories.UserRepository;
import com.example.holidayplanner.repositories.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService appUserDetailsService;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private String adminPass;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder,
                       UserDetailsService appUserDetailsService, UserRoleService userRoleService, ModelMapper modelMapper, @Value("${app.default.admin.password}") String adminPass) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.adminPass = adminPass;
    }

    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);

            adminRole = userRoleRepository.save(adminRole);
            moderatorRole = userRoleRepository.save(moderatorRole);

            initAdmin(List.of(adminRole, moderatorRole));
            initModerator(List.of(moderatorRole));
            initUser(List.of());


        }
    }

    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity().
                setUserRoles(roles).
                setFirstName("Admin").
                setLastName("Adminov").
                setEmail("admin@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity().
                setUserRoles(roles).
                setFirstName("Moderator").
                setLastName("Moderatorov").
                setEmail("moderator@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRoleEntity> roles) {
        UserEntity user = new UserEntity().
                setUserRoles(roles).
                setFirstName("User").
                setLastName("Userov").
                setEmail("user@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(user);
    }


    public void registerAndLogin(UserServiceModel userServiceModel) {
        UserEntity newUser = modelMapper.map(userServiceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        userRepository.save(newUser);

        UserDetails userDetails =
                appUserDetailsService.loadUserByUsername(newUser.getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
    }

    public UserEntity getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
    }

    public boolean hasRole(String role, Long id){
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity==null){
            return false;
        }
        List<UserRoleEntity> userRoles = userEntity.getUserRoles();

        for (UserRoleEntity userRole : userRoles) {
            if(userRole.getUserRole().toString().equals(role)){
                return true;
            }
        }
        return false;
    }

    @Transactional
    public List<UserViewModel> findByEmail(UserServiceModel userServiceModel){
        return userRepository.findAllByEmail(userServiceModel.getEmail()).stream().map(userEntity -> modelMapper.map(userEntity, UserViewModel.class)).collect(Collectors.toList());
    }

    @Transactional
    public UserViewModel findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return modelMapper.map(userEntity, UserViewModel.class);
    }

    public void revokeMod(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity==null){
            return;
        }
        userEntity.getUserRoles().removeIf(userRole -> userRole.getUserRole().toString().equals("MODERATOR"));
        userRepository.save(userEntity);
    }

    public void grantMod(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity==null){
            return;
        }
        userEntity.getUserRoles().add(userRoleService.findByUserRole(UserRoleEnum.MODERATOR));
        userRepository.save(userEntity);
    }

    public void editUsername(UserEditBindingModel userEditBindingModel, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity==null){
            return;
        }
        userEntity.setEmail(userEditBindingModel.getEmail());
        userRepository.save(userEntity);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String updatedUsername = userEditBindingModel.getEmail(); // Replace with the updated username
        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(updatedUsername, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

    }
}
