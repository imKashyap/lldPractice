package onlineAuctionSystem.services;

import java.util.Optional;

import onlineAuctionSystem.models.user.User;
import onlineAuctionSystem.repository.UserRepository;
import onlineAuctionSystem.repository.inmemory.InMemoryUserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new InMemoryUserRepository();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findMember(String username) {
        return userRepository.findByUsername(username);
    }

}
