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
@Table(name = "grammar_rules_telugu")
public class GrammarRulesTelugu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "src_language")
    private String srcLanguage;

    @Column(name = "src_string")
    private String srcString;

    @Column(name = "target_language")
    private String targetLanguage;

    @Column(name = "target_string")
    private String targetString;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_timestamp")
    private Date createdTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_timestamp")
    
    private Date updatedTimestamp;

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

    public String getTargetString() {
        return targetString;
    }

    public void setTargetString(String targetString) {
        this.targetString = targetString;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    

}
