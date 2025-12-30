package stackOverflow.search;

import stackOverflow.post.Question;
import stackOverflow.post.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchByTag extends SearchStrategy{

    @Override
    public List<Question> search(String searchKey) {

        List<Question> questions = new ArrayList<>();

         questionList.forEach(
                 question -> {
                     Set<Tag> tags = question.getTags();
                     if(tags.contains(searchKey)){
                         questions.add(question);
                     }
                 }
                 );
         return  questions;
    }
}
