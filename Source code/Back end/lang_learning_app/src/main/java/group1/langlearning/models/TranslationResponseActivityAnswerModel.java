package group1.langlearning.models;

import java.util.List;

public class TranslationResponseActivityAnswerModel {

    private boolean result;
    private List<QuizReport> quizReport;
    private int scores;
    private int total;
    
    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public List<QuizReport> getQuizReport() {
        return quizReport;
    }
    public void setQuizReport(List<QuizReport> quizReport) {
        this.quizReport = quizReport;
    }
    public int getScores() {
        return scores;
    }
    public void setScores(int scores) {
        this.scores = scores;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    
}