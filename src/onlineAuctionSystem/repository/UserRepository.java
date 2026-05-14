package onlineAuctionSystem.repository;

import java.util.Optional;

import onlineAuctionSystem.models.user.User;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUsername(String username);

}
