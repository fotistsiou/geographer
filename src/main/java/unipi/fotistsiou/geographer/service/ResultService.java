package unipi.fotistsiou.geographer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unipi.fotistsiou.geographer.entity.Question;
import unipi.fotistsiou.geographer.entity.QuestionForm;
import unipi.fotistsiou.geographer.entity.Result;
import unipi.fotistsiou.geographer.repository.ResultRepository;

@Service
public class ResultService {
    private final ResultRepository resultRepository;
    @Autowired
    QuestionForm qForm;
    @Autowired
    Question question;

    @Autowired
    public ResultService (
        ResultRepository resultRepository
    ){
        this.resultRepository = resultRepository;
    }

    public int getResult(QuestionForm qForm) {
        int correct = 0;
        for(Question q: qForm.getQuestions())
            if(q.getAnswer() == q.getChoice()) {
                correct++;
            }


        return correct;
    }
    public void saveResult(Result result) {
        Result saveResult = new Result();
        saveResult.setUser(result.getUser());
        saveResult.setChapter(result.getChapter());
        saveResult.setTotalCorrect(result.getTotalCorrect());
        resultRepository.save(result);
    }
}
