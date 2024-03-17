package group1.langlearning.models;

public class TranslationResponseActivity {
    
    private int questionNo;
    private String inputText;
    private String correctAnswer;
    private String[] mcqOptions;
    private int questionSubmissionId;
    private int totalQuestions;
    
    public int getTotalQuestions() {
        return totalQuestions;
    }
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
    public int getQuestionNo() {
        return questionNo;
    }
    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }
    public String getInputText() {
        return inputText;
    }
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public String[] getMcqOptions() {
        return mcqOptions;
    }
    public void setMcqOptions(String[] mcqOptions) {
        this.mcqOptions = mcqOptions;
    }
    public int getQuestionSubmissionId() {
        return questionSubmissionId;
    }
    public void setQuestionSubmissionId(int questionSubmissionId) {
        this.questionSubmissionId = questionSubmissionId;
    }

    
}
