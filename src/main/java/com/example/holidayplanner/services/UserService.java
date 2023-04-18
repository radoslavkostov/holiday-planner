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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService appUserDetailsService;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserDetailsService appUserDetailsService, UserRoleService userRoleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
    }

    public void registerAndLogin(UserServiceModel userServiceModel) {
        UserEntity newUser = modelMapper.map(userServiceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        userRepository.save(newUser);

        UserDetails userDetails =
                appUserDetailsService.loadUserByUsername(newUser.getUsername());

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
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
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
    public List<UserViewModel> findByUsername(UserServiceModel userServiceModel){
        return userRepository.findAllByUsername(userServiceModel.getUsername()).stream().map(userEntity -> modelMapper.map(userEntity, UserViewModel.class)).collect(Collectors.toList());
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
        userEntity.setUsername(userEditBindingModel.getUsername());
        userRepository.save(userEntity);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String updatedUsername = userEditBindingModel.getUsername();
        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(updatedUsername, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

    }

    public boolean checkIfUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
