package stackOverflow.search;

import stackOverflow.post.Question;

import java.util.List;

public class SearchByAuthor extends SearchStrategy{
    @Override
    public List<Question> search(String searchKey) {
        return questionList.stream()
                .filter(question -> question.getAuthor().getName().equals(searchKey))
                .toList();
    }
}
