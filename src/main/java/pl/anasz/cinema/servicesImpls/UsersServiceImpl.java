package pl.anasz.cinema.servicesImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.anasz.cinema.enities.User;
import pl.anasz.cinema.repositories.UserRepository;
import pl.anasz.cinema.services.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    private UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
