package onlineAuctionSystem.repository.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import onlineAuctionSystem.models.user.User;
import onlineAuctionSystem.repository.UserRepository;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> userList;

    public InMemoryUserRepository() {
        userList = new ConcurrentHashMap<>();
    }

    @Override
    public void save(User user) {
        userList.put(user.getAccount().getUsername(), user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userList.values().stream()
                .filter(user -> user.getAccount().getUsername().equals(username))
                .findFirst();
    }

}
