package group1.langlearning.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import group1.langlearning.entity.McqQuizReport;

public interface McqQuizReportRepository extends CrudRepository<McqQuizReport,Integer>{
    
    McqQuizReport findById(int id);

    // @Query(nativeQuery = true, value="SELECT * FROM language_learning_app.mcq_quiz_report where activity=?1 and username=?2 and activity_completed_flag = 0 and total<?3 order by 1 desc limit 1" )
    // Scores fetchUsername(int activity,String username,int questions);
    @Query(nativeQuery = true, value="SELECT * FROM language_learning_app.mcq_quiz_report where activity=?1 and username=?2 and target_language=?3  order by 1 desc limit 1" )
    McqQuizReport fetchUsername(int activity,String username,String targetLanguage);

    List<McqQuizReport> findByActivityAndActivityCycleNumberAndTargetLanguage(Integer activity,Integer activityCycleNumber,String targetLanguage);
    
    @Query(nativeQuery = true, value = "SELECT count(*) FROM language_learning_app.mcq_quiz_report where activity=?1 && target_language=?2 && activity_cycle_number=?3")
    Integer fetchCount(int activity,String language,int cycleNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM language_learning_app.mcq_quiz_report where activity=?1 && target_language=?2 && activity_cycle_number=?3")
    List<McqQuizReport> fetchQuestionNumberRecords(int activity,String language,int cycleNumber);
}
