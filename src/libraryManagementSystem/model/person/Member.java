package libraryManagementSystem.model.person;

public class Member extends Person {

    private final ContactInfo contactInfo;
    private final Account account;
    private volatile MemberStatus status;

    public Member(String name, ContactInfo contactInfo, Account account) {
        super(name);
        this.contactInfo = contactInfo;
        this.account = account;
        this.status = MemberStatus.ACTIVE;
    }

    public ContactInfo getEmailAddress() {
        return contactInfo;
    }

    public Account getAccount() {
        return account;
    }

    public synchronized MemberStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(MemberStatus status) {
        this.status = status;
    }

}
