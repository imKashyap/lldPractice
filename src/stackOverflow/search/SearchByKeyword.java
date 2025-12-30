package stackOverflow.search;

import stackOverflow.post.Question;

import java.util.List;

public class SearchByKeyword extends SearchStrategy{
    @Override
    public List<Question> search(String searchKey) {
        return questionList.stream()
                .filter(question -> question.getContent().contains(searchKey))
                .toList();
    }
}
