package group1.langlearning.models;

public class TranslationRequestActivityAnswerModel {
    
    private String optionAnswer;
    private int questionSubmissionId;

    public String getOptionAnswer() {
        return optionAnswer;
    }
    public void setOptionAnswer(String optionAnswer) {
        this.optionAnswer = optionAnswer;
    }
    public int getQuestionSubmissionId() {
        return questionSubmissionId;
    }
    public void setQuestionSubmissionId(int questionSubmissionId) {
        this.questionSubmissionId = questionSubmissionId;
    }

}
