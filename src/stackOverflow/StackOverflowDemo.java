package stackOverflow;

import stackOverflow.post.*;
import stackOverflow.search.SearchByAuthor;
import stackOverflow.user.Member;
import stackOverflow.user.Moderator;

import java.util.List;

public class StackOverflowDemo {
    public static void main(String[] args) {
        Member alice = new Member("Alice");
        Member bob = new Member("Bob");
        Member john = new Member("John");

        Moderator mod = new Moderator("Mod");

        Manager manager = Manager.getInstance();
        Question q1 = manager.createQuestion("What is Polymorphism?", alice);
        manager.createTag(new Tag("java"), q1);

        Answer a1 = manager.createAnswer("It's the ability to present the same interface for differing underlying forms.", bob, q1);
        manager.createComment(a1, "Right answer", john);
        manager.createVote(q1, new Vote(bob, VoteType.UPVOTE));
        manager.createFlag(q1, new Flag(bob, "Duplicate content"));

        List<Question> questions = manager.searchQuestions(new SearchByAuthor(), "Alice");
        for(Question q: questions){
            System.out.println(q.getContent());
        }
        mod.deletePost(q1);
        int currentReputation = alice.getCurrentReputation();
        System.out.println("Alice's Reputation:" +currentReputation);
    }
}
