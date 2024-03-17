package group1.langlearning.models;

public class TranslationRequestModel {
    
    private String sourceLanguage;
    private String targetLanguage;
    private String inputText;
    
    public String getSourceLanguage() {
        return sourceLanguage;
    }
    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }
    public String getTargetLanguage() {
        return targetLanguage;
    }
    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
    public String getInputText() {
        return inputText;
    }
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    
}
