package stackOverflow;

import stackOverflow.post.*;
import stackOverflow.search.SearchStrategy;
import stackOverflow.user.Member;
import stackOverflow.user.Observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Manager {
    private static final Manager INSTANCE =  new Manager();
    private final List<Question> questionList =new CopyOnWriteArrayList<>();
    private Manager(){
    }

    public static Manager getInstance(){
        return INSTANCE;
    }

    public Question createQuestion(String body, Member author){
        Question question = (Question) PostFactory.createPost(PostType.QUESTION, body, author);
        questionList.add(question);
        return question;
    }

    public Answer createAnswer(String body, Member author, Question question){
        Answer answer = (Answer) PostFactory.createPost(PostType.ANSWER,body, author);
        question.addAnswer(answer);
        Observer member = question.getAuthor();
        member.update(answer);
        return answer;
    }

    public void createComment(Commentable commentablePost, String body, Member author){
        Comment comment = (Comment) PostFactory.createPost(PostType.COMMENT, body,author);
        commentablePost.comment(comment);
        Observer member = ((Post) commentablePost).getAuthor();
        member.update(comment);
    }

    public void createVote(Votable votablePost, Vote vote){
        votablePost.vote(vote);
        Observer member = ((Post) votablePost).getAuthor();
        member.update(vote);
    }

    public void createTag(Tag tag, Question question){
        question.addTag(tag);
    }

    public void createFlag(Flaggable post, Flag flag){
        post.flagBy(flag);
    }

    public List<Question> getAllQuestions(){
        return questionList;
    }

    public List<Question> searchQuestions(SearchStrategy strategy, String searchKey){
        return strategy.search(searchKey);
    }
}
