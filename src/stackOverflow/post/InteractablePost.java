package stackOverflow.post;

import stackOverflow.user.Member;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public  class InteractablePost extends Post implements Flaggable, Votable, Commentable{
    List<Flag> flags = new CopyOnWriteArrayList<>();
    List<Vote> votes = new CopyOnWriteArrayList<>();
    List<Comment> comments = new CopyOnWriteArrayList<>();

    public InteractablePost(String content, Member author) {
        super(content, author);
    }

    @Override
    public void flagBy(Flag flag) {
       flags.add(flag);
    }

    @Override
    public void vote(Vote vote) {
        votes.add(vote);
    }

    @Override
    public void comment(Comment comment) {
        comments.add(comment);
    }
}
