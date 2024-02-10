package org.example.services;

import org.example.data.model.Profile;
import org.example.data.model.User;
import org.example.data.repository.UserRepository;
import org.example.dto.request.LoginRequest;
import org.example.dto.request.RegisterRequest;
import org.example.exception.ActionDoneException;
import org.example.exception.InvalidFormatDetailException;
import org.example.exception.InvalidLoginDetail;
import org.example.exception.UserExistException;
import org.example.util.Mapper;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileService profileService;
    @Override
    public void register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getEmail())) throw new UserExistException("User already exist");
        if (!Validation.validateEmail(registerRequest.getEmail())) throw new InvalidFormatDetailException("Invalid format for email");
        if (!Validation.validatePassword(registerRequest.getPassword())) throw new InvalidFormatDetailException("Password is weak ensure start with capital letter and up to 7 digit");
        if (!Validation.validateDate(registerRequest.getDateOfBirth())) throw new InvalidFormatDetailException("Format for date of birth is dd/mm/yyyy");
        Profile profile = Mapper.mapProfile(registerRequest);
        User newUser = new User();
        newUser.setUserProfile(profile);
        userRepository.save(newUser);
        profileService.saveProfile(profile);
    }

    @Override
    public void login(LoginRequest loginRequest) {
        if (!userExist(loginRequest.getEmail())) throw new InvalidLoginDetail("Invalid login detail");
        Profile profile = profileService.findProfileByEmail(loginRequest.getEmail());
        if (!profile.getPassword().equals(loginRequest.getPassword())) throw new InvalidLoginDetail("Invalid login detail");
        User newUser = userRepository.findUserByUserProfile(profile);
        if (newUser.isLoginStatus()) throw new ActionDoneException("User has login already");
        newUser.setLoginStatus(true);
        userRepository.save(newUser);
    }

    private boolean userExist(String email) {
        return profileService.findProfileByEmail(email) != null;
    }
}
