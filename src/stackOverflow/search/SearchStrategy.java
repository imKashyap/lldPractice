package stackOverflow.search;

import stackOverflow.Manager;
import stackOverflow.post.Question;

import java.util.List;

public abstract class SearchStrategy {
     protected final List<Question> questionList;
     SearchStrategy(){
         Manager manager = Manager.getInstance();
         questionList = manager.getAllQuestions();
     }
     public abstract  List<Question> search(String searchKey);
}
