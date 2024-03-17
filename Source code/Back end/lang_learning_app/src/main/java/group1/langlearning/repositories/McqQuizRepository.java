package group1.langlearning.repositories;

import org.springframework.data.repository.CrudRepository;

import group1.langlearning.entity.McqQuiz;

public interface McqQuizRepository extends CrudRepository<McqQuiz,Integer> {
    
    McqQuiz findById(int id);
}
