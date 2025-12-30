package stackOverflow.post;

import stackOverflow.user.Member;
import stackOverflow.utils.IdGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private final String id;
    private final String content;
    private final String createdAt;
    private final Member author;

    public Post(String content, Member author) {
        this.id = IdGenerator.generateId("PO-");
        this.content = content;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public Member getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", author=" + author +
                '}';
    }
}
