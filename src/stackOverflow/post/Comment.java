package stackOverflow.post;

import stackOverflow.user.Member;

public class Comment extends Post{
    public Comment(String content, Member author) {
        super(content, author);
    }
}
