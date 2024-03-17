package group1.langlearning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "grammar_rules_sentences")
public class GrammarRulesSentences {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "grammar_rule")
    private String grammarRule;

    @Column(name = "englishSentence")
    private String english_sentence;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrammarRule() {
        return grammarRule;
    }

    public void setGrammarRule(String grammarRule) {
        this.grammarRule = grammarRule;
    }

    public String getEnglish_sentence() {
        return english_sentence;
    }

    public void setEnglish_sentence(String english_sentence) {
        this.english_sentence = english_sentence;
    }

    
}
