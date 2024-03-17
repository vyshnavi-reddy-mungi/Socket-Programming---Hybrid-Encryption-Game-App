
package group1.langlearning.models;

public class Translation {
    private String word;
    private String translation;

    public Translation(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
