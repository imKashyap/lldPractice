package onlineAuctionSystem.models.user;

public class User {
    private final Account account;
    private final String name;

    public User(Account account, String name) {
        this.account = account;
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

}
