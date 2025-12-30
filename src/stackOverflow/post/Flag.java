package stackOverflow.post;

import stackOverflow.user.Member;

public class Flag {
    final Member reporter;
    final String reason;

    public Flag(Member reporter, String reason) {
        this.reporter = reporter;
        this.reason = reason;
    }
}
