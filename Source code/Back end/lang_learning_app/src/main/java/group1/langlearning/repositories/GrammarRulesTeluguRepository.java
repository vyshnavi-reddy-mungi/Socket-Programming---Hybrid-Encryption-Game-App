package group1.langlearning.repositories;

import org.springframework.data.repository.CrudRepository;

import group1.langlearning.entity.GrammarRulesTelugu;

public interface GrammarRulesTeluguRepository extends CrudRepository<GrammarRulesTelugu,Integer> {
    
    GrammarRulesTelugu findById(int id);
}
