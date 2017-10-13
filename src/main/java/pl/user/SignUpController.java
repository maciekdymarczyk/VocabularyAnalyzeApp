package pl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//import org.springframework.security.crypto.password.PasswordEncoder;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class SignUpController {

    //    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
//    public SignUpController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    public SignUpController(UserRepository userRepository) {
//        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    //TODO I should move it to service? Switch post to put?
    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }
}
