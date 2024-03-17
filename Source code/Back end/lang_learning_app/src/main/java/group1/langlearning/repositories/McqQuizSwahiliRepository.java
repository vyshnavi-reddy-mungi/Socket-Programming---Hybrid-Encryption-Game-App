package group1.langlearning.repositories;

import org.springframework.data.repository.CrudRepository;

import group1.langlearning.entity.McqQuizSwahili;

public interface McqQuizSwahiliRepository extends CrudRepository<McqQuizSwahili,Integer> {
    
    McqQuizSwahili findById(int id);

    McqQuizSwahili findByInput(String verb);

    McqQuizSwahili findByCorrectAnswer(String verb);
}

