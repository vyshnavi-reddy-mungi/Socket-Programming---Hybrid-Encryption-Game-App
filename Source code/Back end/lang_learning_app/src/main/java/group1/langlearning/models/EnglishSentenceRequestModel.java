package group1.langlearning.models;

public class EnglishSentenceRequestModel {
    
    private String verb;
    private String tense; 
    private String subject; 
    private String feature;
    private String impcheck;
    private String englishVerb;
    
    public String getEnglishVerb() {
        return englishVerb;
    }
    public void setEnglishVerb(String englishVerb) {
        this.englishVerb = englishVerb;
    }
    public String getVerb() {
        return verb;
    }
    public void setVerb(String verb) {
        this.verb = verb;
    }
    public String getTense() {
        return tense;
    }
    public void setTense(String tense) {
        this.tense = tense;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getFeature() {
        return feature;
    }
    public void setFeature(String feature) {
        this.feature = feature;
    }
    public String getImpcheck() {
        return impcheck;
    }
    public void setImpcheck(String impcheck) {
        this.impcheck = impcheck;
    }

    
}
