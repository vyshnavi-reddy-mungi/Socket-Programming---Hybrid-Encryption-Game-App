package group1.langlearning.repositories;

import org.springframework.data.repository.CrudRepository;

import group1.langlearning.entity.GrammarRulesSentences;

public interface GrammarRulesSentencesRepository extends CrudRepository<GrammarRulesSentences,Integer> {
    
    GrammarRulesSentences findById(int id);
}
   
