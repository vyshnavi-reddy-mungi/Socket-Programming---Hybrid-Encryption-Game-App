package group1.langlearning.models;

public class QuizReport {
    
    private String question;
    private String answer;
    private String givenAnswer;
    private Integer answerFlag;
    
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getGivenAnswer() {
        return givenAnswer;
    }
    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
    public Integer getAnswerFlag() {
        return answerFlag;
    }
    public void setAnswerFlag(Integer answerFlag) {
        this.answerFlag = answerFlag;
    }
    
    
}
