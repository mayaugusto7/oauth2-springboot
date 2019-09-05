package br.com.cast.castinside.controller;

import br.com.cast.castinside.model.User;
import br.com.cast.castinside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Secured({ "ROLE_ADMIN", "ROLE_SUPERVISOR" })
    @RequestMapping(method = RequestMethod.GET)
    public List<User> allUsers() {
        return this.userRepository.findAll();
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR"})
    @RequestMapping(value = "/pagination", method = RequestMethod.GET)
    public Page<User> list(@RequestParam("page") int page, @RequestParam("size") int size) {
        return this.userRepository.findAll(PageRequest.of(page, size));
    }

    @RequestMapping(method = RequestMethod.POST)
    public User save(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        this.userRepository.deleteById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User details(@PathVariable("id") Long id) {
       return this.userRepository.findById(id).get();
    }
}
