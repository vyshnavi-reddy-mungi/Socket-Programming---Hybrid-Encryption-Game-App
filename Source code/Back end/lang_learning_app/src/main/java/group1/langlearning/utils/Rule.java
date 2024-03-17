package group1.langlearning.utils;

public class Rule {
    Integer id;
    String srcLang;
    String srcString;
    String tarLang;
    String tarString;
public Rule()
{
    
}
   
    public Rule(Integer id,String srcLang, String srcString, String tarLang, String tarString){
        this.id = id;
        this.srcLang = srcLang;
        this.srcString = srcString;
        this.tarLang = tarLang;
        this.tarString = tarString;
    }

   
    public void printRule(Rule rule, String src) {
        if(src.equals(srcLang)) {
            System.out.println(srcString + " - > " + tarString);
        }

    }
}
