package stackOverflow.post;

import stackOverflow.user.Member;

public class PostFactory {
    public static Post createPost(PostType type, String content, Member member) {
        return switch (type) {
            case QUESTION -> new Question(content, member);
            case ANSWER -> new Answer(content, member);
            case COMMENT -> new Comment(content, member);
            default -> throw new IllegalArgumentException("Unknown entity type");
        };
    }
}
