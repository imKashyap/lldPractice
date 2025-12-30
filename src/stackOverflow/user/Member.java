package stackOverflow.user;

import stackOverflow.post.Answer;
import stackOverflow.post.Comment;
import stackOverflow.post.Vote;
import stackOverflow.post.VoteType;
import stackOverflow.utils.IdGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class Member extends User implements Observer {
    private final String id;
    private final AtomicInteger reputationScore = new AtomicInteger(0);

    public Member(String name) {
        super(name);
        this.id = IdGenerator.generateId("US-");
    }


    @Override
    public void update(Object obj) {
        if(obj instanceof Answer){
            System.out.println("Your Question is answered.");
        }
        else if(obj instanceof Comment comment){
            System.out.println("Your post has a comment.");
        }
        else if(obj instanceof Vote vote){
            if( vote.getType() == VoteType.UPVOTE){
                reputationScore.incrementAndGet();
            }
            else if(vote.getType() == VoteType.DOWNVOTE){
                reputationScore.decrementAndGet();
            }
        }
    }

    public int getCurrentReputation(){
        return reputationScore.get();
    }
}
