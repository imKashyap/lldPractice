package stackOverflow.post;

import stackOverflow.user.Member;

public class Vote {
   private final Member voter;
   private final VoteType type;

    public Vote(Member voter, VoteType voteType) {
        this.voter = voter;
        this.type = voteType;
    }

    public Member getVoter() {
        return voter;
    }

    public VoteType getType() {
        return type;
    }
}