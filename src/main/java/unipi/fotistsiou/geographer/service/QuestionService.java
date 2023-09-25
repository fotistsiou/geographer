package unipi.fotistsiou.geographer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unipi.fotistsiou.geographer.entity.Question;
import unipi.fotistsiou.geographer.entity.QuestionForm;
import unipi.fotistsiou.geographer.repository.QuestionRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionForm questionForm;

    @Autowired
    public QuestionService (
        QuestionRepository questionRepository,
        QuestionForm questionForm
    ){
        this.questionRepository = questionRepository;
        this.questionForm = questionForm;
    }

    public QuestionForm getQuestionsByChapter(String chapter) {
        List<Question> allQuestions = questionRepository.findAll();
        List<Question> quizList = new ArrayList<>();

        for (Question question:allQuestions) {
            if (question.getChapter().equals(chapter)) {
                quizList.add(question);
            }
        }

        questionForm.setQuestions(quizList);

        return questionForm;
    }
}