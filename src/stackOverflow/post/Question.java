package stackOverflow.post;

import stackOverflow.user.Member;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Question extends InteractablePost{
    Set<Tag> tags = ConcurrentHashMap.newKeySet();
    List<Answer> answers = new CopyOnWriteArrayList<>();

    public Question(String content, Member author) {
        super(content, author);
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }

    public Set<Tag> getTags(){
        return tags;
    }


}
