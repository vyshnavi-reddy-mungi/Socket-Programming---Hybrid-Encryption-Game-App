package group1.langlearning.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table
@Entity(name = "mcq_quiz_report")
public class McqQuizReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "question")
    private String question;

    @Column(name = "options")
    private String options;

    @Column(name = "given_answer")
    private String givenAnswer;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "answer_flag")
    private boolean answerFlag;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_timestamp")
    private Date createdTimestamp;

    @Column(name = "activity")
    private Integer activity;

    @Column(name = "activity_cycle_number")
    private Integer activityCycleNumber;

    @Column(name = "question_number")
    private Integer questionNumber;

    @Column(name = "activity_completed_flag")
    private Integer activityCompletedFlag;

    @Column(name = "source_language")
    private String sourceLanguage;

    @Column(name = "target_language")
    private String targetLanguage;

   
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(boolean answerFlag) {
        this.answerFlag = answerFlag;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getActivityCycleNumber() {
        return activityCycleNumber;
    }

    public void setActivityCycleNumber(Integer activityCycleNumber) {
        this.activityCycleNumber = activityCycleNumber;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getActivityCompletedFlag() {
        return activityCompletedFlag;
    }

    public void setActivityCompletedFlag(Integer activityCompletedFlag) {
        this.activityCompletedFlag = activityCompletedFlag;
    }

  
    
    
}
