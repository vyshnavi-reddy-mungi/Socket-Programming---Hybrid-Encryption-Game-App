// package group1.langlearning.repositories;

// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.CrudRepository;

// import group1.langlearning.entity.Scores;

// public interface ScoresRepository  extends CrudRepository<Scores, Integer> {
    
//     Scores findByUsername(String username);
   
//     @Query(nativeQuery = true, value="SELECT * FROM language_learning_app.scores where activity=?1 and username=?2 and activity_completed_flag = 0 and total<?3 order by 1 desc limit 1" )
//     Scores fetchUsername(int activity,String username,int questions);

//     Scores findById(int id);
// }
