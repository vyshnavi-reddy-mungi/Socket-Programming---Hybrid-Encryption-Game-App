package group1.langlearning.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TranslationResponseModel {
    
    // private String username;
    // private String outputText;
    // private String inputText;
    // private String correctAnswer;
    // private String optionAnswer;
    // private String[] mcqOptions;
    // private boolean result;
    // private int scores;
    // private int questionNo;
    // private int total;
    // private int questionSubmissionId;
    // private String sourceLanguage;
    // private String targetLanguage;
    // private List<QuizReport> quizReport;

    private String username;
    private String outputText;
    private String inputText;
    private String correctAnswer;
    private String optionAnswer;
    private String[] mcqOptions;
    private boolean result;
    private int scores;
    private int questionNo;
    private int total;
    private int questionSubmissionId;
    private String sourceLanguage;
    private String targetLanguage;
    private List<QuizReport> quizReport;

   

    public List<QuizReport> getQuizReport() {
        return quizReport;
    }

    public void setQuizReport(List<QuizReport> quizReport) {
        this.quizReport = quizReport;
    }

    

    public String[] getMcqOptions() {
        return mcqOptions;
    }

    public void setMcqOptions(String[] mcqOptions) {
        this.mcqOptions = mcqOptions;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    
    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getOptionAnswer() {
        return optionAnswer;
    }

    public void setOptionAnswer(String optionAnswer) {
        this.optionAnswer = optionAnswer;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public int getQuestionSubmissionId() {
        return questionSubmissionId;
    }

    public void setQuestionSubmissionId(int questionSubmissionId) {
        this.questionSubmissionId = questionSubmissionId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
