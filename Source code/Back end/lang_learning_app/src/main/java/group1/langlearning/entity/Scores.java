// package group1.langlearning.entity;

// import java.util.Date;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import jakarta.persistence.Temporal;
// import jakarta.persistence.TemporalType;

// @Entity
// @Table(name = "scores")
// public class Scores {
  
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private int id;

//     @Column(name = "username")
//     private String username;

//     @Column(name = "correct_answers")
//     private int correctAnswers;

//     @Column(name = "total")
//     private int total;

//     @Temporal(TemporalType.TIMESTAMP)
//     @Column(name = "created_timestamp")
//     private Date createdTimestamp;

//     @Column(name = "activity_completed_flag")
//     private int activityCompletedFlag;

//     @Column(name = "activity")
//     private int activity;

//     public int getId() {
//         return id;
//     }

//     public void setId(int id) {
//         this.id = id;
//     }

//     public String getUsername() {
//         return username;
//     }

//     public void setUsername(String username) {
//         this.username = username;
//     }

//     public int getCorrectAnswers() {
//         return correctAnswers;
//     }

//     public void setCorrectAnswers(int correctAnswers) {
//         this.correctAnswers = correctAnswers;
//     }

//     public int getTotal() {
//         return total;
//     }

//     public void setTotal(int total) {
//         this.total = total;
//     }

//     public Date getCreatedTimestamp() {
//         return createdTimestamp;
//     }

//     public void setCreatedTimestamp(Date createdTimestamp) {
//         this.createdTimestamp = createdTimestamp;
//     }

//     public int getActivityCompletedFlag() {
//         return activityCompletedFlag;
//     }

//     public void setActivityCompletedFlag(int activityCompletedFlag) {
//         this.activityCompletedFlag = activityCompletedFlag;
//     }

//     public int getActivity() {
//         return activity;
//     }

//     public void setActivity(int activity) {
//         this.activity = activity;
//     }

    
// }
