package group1.langlearning.utils;

public class Rule_1 {
    
    public String srcLang;
    public String srcString;
    public String tarLang;
    public String tarString;

    public Rule_1(String srcLang, String srcString, String tarLang, String tarString){
        this.srcLang = srcLang;
        this.srcString = srcString;
        this.tarLang = tarLang;
        this.tarString = tarString;
    }


    public String getSrcLang() {
        return srcLang;
    }
    public void setSrcLang(String srcLang) {
        this.srcLang = srcLang;
    }
    public String getSrcString() {
        return srcString;
    }
    public void setSrcString(String srcString) {
        this.srcString = srcString;
    }
    public String getTarLang() {
        return tarLang;
    }
    public void setTarLang(String tarLang) {
        this.tarLang = tarLang;
    }
    public String getTarString() {
        return tarString;
    }

    public void setTarString(String tarString) {
        this.tarString = tarString;
    }

    public void printRule() {
        System.out.println(this.srcString + " -> " + this.tarString);

    }
}