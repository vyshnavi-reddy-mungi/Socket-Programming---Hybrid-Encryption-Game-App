package group1.langlearning.repositories;

import org.springframework.data.repository.CrudRepository;

import group1.langlearning.entity.GrammarRulesSwahili;

public interface GrammarRulesSwahiliRepository extends CrudRepository<GrammarRulesSwahili, Integer> {
    
    GrammarRulesSwahili findById(int id);
}
