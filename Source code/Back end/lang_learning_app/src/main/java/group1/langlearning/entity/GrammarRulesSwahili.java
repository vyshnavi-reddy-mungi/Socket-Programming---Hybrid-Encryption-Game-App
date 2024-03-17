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

@Entity
@Table(name = "grammar_rules_swahili")
public class GrammarRulesSwahili {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "src_language")
    private String srcLanguage;

    @Column(name = "src_string")
    private String srcString;

    @Column(name = "target_language")
    private String targetLanguage;

    @Column(name = "grammar_rule")
    private String grammarRule;

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrcLanguage() {
        return srcLanguage;
    }

    public void setSrcLanguage(String srcLanguage) {
        this.srcLanguage = srcLanguage;
    }

    public String getSrcString() {
        return srcString;
    }

    public void setSrcString(String srcString) {
        this.srcString = srcString;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getGrammarRule() {
        return grammarRule;
    }

    public void setGrammarRule(String grammarRule) {
        this.grammarRule = grammarRule;
    }

   

  

}
