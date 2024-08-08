package Controllers;

import Dtos.ResponseStatus;
import Dtos.SignUpRequestDto;
import Dtos.SignUpResponseDto;
import Services.UserService;
import models.User;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    public UserController( UserService userService ) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto request ){
        SignUpResponseDto response = new SignUpResponseDto();
        User user;

        try{
            user = userService.signup( request.getName(), request.getEmail(), request.getPassword() );
            response.setResponseStatus( ResponseStatus.SUCCESS);
            response.setUserId( user.getId() );
        }catch ( Exception ex ){
            response.setResponseStatus( ResponseStatus.FAILURE );
        }
        return response;
    }

    public Optional<User> fetchUserName(String email ){
        return userService.findUserUsingMailId(email);
    }
}