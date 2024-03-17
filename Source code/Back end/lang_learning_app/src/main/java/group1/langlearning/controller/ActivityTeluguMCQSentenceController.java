package group1.langlearning.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import group1.langlearning.repositories.GrammarRulesSentencesRepository;
import group1.langlearning.repositories.GrammarRulesSwahiliRepository;
import group1.langlearning.repositories.GrammarRulesTeluguRepository;
import group1.langlearning.repositories.McqQuizReportRepository;
import group1.langlearning.repositories.McqQuizSwahiliRepository;
// import group1.langlearning.utils.Language;
import group1.langlearning.utils.Language_1;
import group1.langlearning.utils.Rule_1;
import group1.langlearning.entity.GrammarRulesSentences;
import group1.langlearning.entity.McqQuizReport;
import group1.langlearning.entity.McqQuizSwahili;
// import group1.langlearning.entity.Scores;
import group1.langlearning.models.Constants;
import group1.langlearning.models.GeneralResponse;
import group1.langlearning.models.QuizReport;
import group1.langlearning.models.SwahiliSentenceRequestModel;
import group1.langlearning.models.TranslationRequestActivity;
import group1.langlearning.models.TranslationRequestActivityAnswerModel;
import group1.langlearning.models.TranslationResponseActivity;
import group1.langlearning.models.TranslationResponseActivityAnswerModel;

@RestController
@RequestMapping(path = "/activity2/new")
@CrossOrigin("*")
public class ActivityTeluguMCQSentenceController {
    
    Gson gson = new GsonBuilder().create(); 

    // @Autowired
    // private ScoresRepository scoresRepository;

    @Autowired
    private GrammarRulesTeluguRepository grammarRulesRepository;

    @Autowired
    private GrammarRulesSwahiliRepository grammarRulesSwahiliRepository;

    @Autowired
    private McqQuizReportRepository mcqQuizReportRepository;

    @Autowired
    private McqQuizSwahiliRepository mcqQuizSwahiliRepository;

    @Autowired
    private GrammarRulesSentencesRepository grammarRulesSentencesRepository;

    private final int MCQ_COUNT = 2;

    private final int TOTAL_GAME_ACTIVITY_QUESTIONS = 187;

    
  
    static Map<String, Language_1> Library = new HashMap<String, Language_1>();
    static Set<String> pronouns = new HashSet<String>();
    static Set<String> verbs = new HashSet<String>();
    static Set<String> auxilaryVerbs = new HashSet<String>();
    static ArrayList<Rule_1> Rules = new ArrayList<Rule_1>();


    
    @PostMapping(value = "/mcq/start")
    public ResponseEntity<GeneralResponse> mcqStart(@RequestBody TranslationRequestActivity requestModel) throws IOException {
        
        TranslationResponseActivity responseModel = new TranslationResponseActivity();
        // Scores scores = scoresRepository.fetchUsername(model.getUsername(),Integer.parseInt(filesutil.getProperty("mcq_count")));
        McqQuizReport mcqQuizReport = mcqQuizReportRepository.fetchUsername(2,requestModel.getUsername(),requestModel.getTargetLanguage());
        Random random = new Random();
        McqQuizReport mcqQuizReportNew = null;
        if(mcqQuizReport != null)
        {
            if(mcqQuizReport.getGivenAnswer() != null)
            {
            mcqQuizReportNew = new McqQuizReport();
            if(mcqQuizReport.getActivityCompletedFlag() != null && mcqQuizReport.getActivityCompletedFlag()== 1)
            {
              mcqQuizReportNew.setActivityCycleNumber(mcqQuizReport.getActivityCycleNumber() + 1);
            //   mcqQuizReportNew.setQuestionNumber(1);
            int randomNumber = random.nextInt(TOTAL_GAME_ACTIVITY_QUESTIONS) + 1;
            mcqQuizReportNew.setQuestionNumber(randomNumber);


            }
           
            mcqQuizReportNew.setUsername(requestModel.getUsername());
            mcqQuizReportNew.setCreatedTimestamp(new Date());
            mcqQuizReportNew.setActivity(2);
            if(mcqQuizReportNew.getQuestionNumber() == null)
            {
            //   mcqQuizReportNew.setQuestionNumber(mcqQuizReport.getQuestionNumber() + 1);
            List<McqQuizReport> listRecords = mcqQuizReportRepository.fetchQuestionNumberRecords(mcqQuizReport.getActivity(), mcqQuizReport.getTargetLanguage(), mcqQuizReport.getActivityCycleNumber());
            ArrayList<Integer> questionNumbers = new ArrayList<>();

            for(McqQuizReport m : listRecords)
                questionNumbers.add(m.getQuestionNumber());
            while(true)
            {
                int randomNumber = random.nextInt(TOTAL_GAME_ACTIVITY_QUESTIONS) + 1;
                if(!questionNumbers.contains(randomNumber))
                {
                mcqQuizReportNew.setQuestionNumber(randomNumber);
                break;
                }
            }
            }
            if(mcqQuizReportNew.getActivityCycleNumber() == null)
              mcqQuizReportNew.setActivityCycleNumber(mcqQuizReport.getActivityCycleNumber());
           
              mcqQuizReportRepository.save(mcqQuizReportNew);
              Integer countQuestions = mcqQuizReportRepository.fetchCount(mcqQuizReportNew.getActivity(),requestModel.getTargetLanguage(),mcqQuizReportNew.getActivityCycleNumber());
          responseModel.setQuestionNo(countQuestions + 1);
            // responseModel.setQuestionNo(mcqQuizReportNew.getQuestionNumber());


        
        // if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("te"))
        // {
          
        // GrammarRulesTelugu rules = grammarRulesRepository.findById(responseModel.getQuestionNo());
        // if(rules != null)
        //     {
        //         responseModel =  fetchOptions(rules,requestModel,responseModel,mcqQuizReportNew,requestModel.getTargetLanguage());
        //     }
        // }
        // else if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("sw"))
        // {
            
        //     GrammarRulesSwahili mcqSwahili = grammarRulesSwahiliRepository.findById(responseModel.getQuestionNo());
        //     McqQuizSwahili verb = mcqQuizSwahiliRepository.findByInput(SENTENCE_VERB);
            
        //     if(mcqSwahili != null && verb != null)
        //     {
        //         responseModel = fetchMCQSwahiliOptions(responseModel, mcqSwahili, verb, mcqQuizReportNew, requestModel.getTargetLanguage());
        //     }
        // }

            //   GrammarRulesSentences rules = grammarRulesSentencesRepository.findById(responseModel.getQuestionNo());
              GrammarRulesSentences rules = grammarRulesSentencesRepository.findById((int)mcqQuizReportNew.getQuestionNumber());
              
              responseModel =  fetchSentenceOptions(rules,requestModel,responseModel,mcqQuizReportNew,requestModel.getTargetLanguage(),random);
              
             
              mcqQuizReportNew.setQuestion(responseModel.getInputText());
              String optionsGiven = "";
              for(String s:responseModel.getMcqOptions())
                   optionsGiven = optionsGiven + s + ",";
              mcqQuizReportNew.setOptions(optionsGiven.substring(0, optionsGiven.length()-1));
              // mcqQuizReportNew.setOptions(model.getMcqOptions().toString());
              mcqQuizReportNew.setCorrectAnswer(responseModel.getCorrectAnswer());
              mcqQuizReportRepository.save(mcqQuizReportNew);
              responseModel.setQuestionSubmissionId(mcqQuizReportNew.getId());


            }


            else{
               
                
                System.out.println("model before grammar " + gson.toJson(responseModel));
        
                        
            //   GrammarRulesSentences rules = grammarRulesSentencesRepository.findById(responseModel.getQuestionNo());
            GrammarRulesSentences rules = grammarRulesSentencesRepository.findById((int)mcqQuizReport.getQuestionNumber());
             
              responseModel =  fetchSentenceOptions(rules,requestModel,responseModel,mcqQuizReport,requestModel.getTargetLanguage(),random);
             
           
                    mcqQuizReport.setQuestion(responseModel.getInputText());
                    String optionsGiven = "";
                    for(String s:responseModel.getMcqOptions())
                         optionsGiven = optionsGiven + s + ",";
                    mcqQuizReport.setOptions(optionsGiven.substring(0, optionsGiven.length()-1));
                    mcqQuizReport.setCorrectAnswer(responseModel.getCorrectAnswer());
                    mcqQuizReport.setCreatedTimestamp(new Date());

                    mcqQuizReportRepository.save(mcqQuizReport);
                    responseModel.setQuestionSubmissionId(mcqQuizReport.getId());
    

                    Integer countQuestions = mcqQuizReportRepository.fetchCount(mcqQuizReport.getActivity(),requestModel.getTargetLanguage(),mcqQuizReport.getActivityCycleNumber());
                    responseModel.setQuestionNo(countQuestions+1);
                   
            }
        }
      

else
{
    mcqQuizReport = new McqQuizReport();
            mcqQuizReport.setUsername(requestModel.getUsername());
            mcqQuizReport.setCreatedTimestamp(new Date());
            mcqQuizReport.setActivity(2);
            // mcqQuizReport.setQuestionNumber(1);
            int randomNumber = random.nextInt(TOTAL_GAME_ACTIVITY_QUESTIONS) + 1;
            mcqQuizReport.setQuestionNumber(randomNumber);

            mcqQuizReport.setActivityCycleNumber(1);

            mcqQuizReportRepository.save(mcqQuizReport);
            Integer countQuestions = mcqQuizReportRepository.fetchCount(mcqQuizReport.getActivity(),requestModel.getTargetLanguage(),mcqQuizReport.getActivityCycleNumber());
            responseModel.setQuestionNo(countQuestions + 1);
            // responseModel.setQuestionNo(mcqQuizReport.getQuestionNumber());
           
             
               
            // GrammarRulesSentences rules = grammarRulesSentencesRepository.findById(responseModel.getQuestionNo());
            GrammarRulesSentences rules = grammarRulesSentencesRepository.findById((int)mcqQuizReport.getQuestionNumber());
             
            responseModel =  fetchSentenceOptions(rules,requestModel,responseModel,mcqQuizReport,requestModel.getTargetLanguage(),random);
             
            
                mcqQuizReport.setQuestion(responseModel.getInputText());
                String optionsGiven = "";
                for(String s:responseModel.getMcqOptions())
                     optionsGiven = optionsGiven + s + ",";
                mcqQuizReport.setOptions(optionsGiven.substring(0, optionsGiven.length()-1));
                // mcqQuizReport.setOptions(model.getMcqOptions().toString());
                mcqQuizReport.setCorrectAnswer(responseModel.getCorrectAnswer());
                mcqQuizReportRepository.save(mcqQuizReport);
                responseModel.setQuestionSubmissionId(mcqQuizReport.getId());

            
          
       
}
System.out.println("model " + gson.toJson(responseModel));
        
            responseModel.setTotalQuestions(MCQ_COUNT);
            return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.TRUE,
        Constants.OPTIONS_SENT, Constants.OPTIONS_SENT, responseModel), HttpStatus.OK);
 
    }
    


    private TranslationResponseActivity fetchSentenceOptions(GrammarRulesSentences rules,
            TranslationRequestActivity requestModel, TranslationResponseActivity responseModel,
            McqQuizReport mcqQuizReportNew, String targetLanguage,Random random) throws IOException {

                responseModel.setInputText(rules.getEnglish_sentence());

                ArrayList<Integer> questionCount = new ArrayList<>();
                questionCount.add(rules.getId());
                while(questionCount.size()<4)
                {
                    int randomNumber = random.nextInt(TOTAL_GAME_ACTIVITY_QUESTIONS) + 1;
                   if(!questionCount.contains(randomNumber))
                   questionCount.add(randomNumber);
        
                }
                List<String> options = new ArrayList<>();

                if(targetLanguage.equals("te"))
                {
                String answerTeluguString = fetchTeluguString(responseModel.getInputText());
                responseModel.setCorrectAnswer(answerTeluguString);
                
                options.add(answerTeluguString);
              
                for(int i = 1; i<4; i++)
                {
                    GrammarRulesSentences teluguRule = grammarRulesSentencesRepository.findById((int)questionCount.get(i));
                    String optionTeluguString = fetchTeluguString(teluguRule.getEnglish_sentence());
                    options.add(optionTeluguString);
                }
                }
                if(targetLanguage.equals("sw"))
                {
                    SwahiliSentenceRequestModel swahiliSentenceRequestModel = new SwahiliSentenceRequestModel();
                swahiliSentenceRequestModel = generateRequestModel(swahiliSentenceRequestModel,rules.getGrammarRule());
                String answerSwahiliString =   getSwahiliSentence(swahiliSentenceRequestModel);
                responseModel.setCorrectAnswer(answerSwahiliString);
                
                options.add(answerSwahiliString);
                for(int i = 1; i<4; i++)
                {
                    GrammarRulesSentences swahiliRule = grammarRulesSentencesRepository.findById((int)questionCount.get(i));
                    swahiliSentenceRequestModel = generateRequestModel(swahiliSentenceRequestModel,swahiliRule.getGrammarRule());//rules.getGrammarRule());
                    String optionSwahiliString =   getSwahiliSentence(swahiliSentenceRequestModel);
                     options.add(optionSwahiliString);
                }
                }
                Collections.shuffle(options);
          
                
                String[] sentenceOptions = new String[options.size()];
                  sentenceOptions = options.toArray(sentenceOptions);
                responseModel.setMcqOptions(sentenceOptions);

                mcqQuizReportNew.setTargetLanguage(targetLanguage);
                mcqQuizReportRepository.save(mcqQuizReportNew);

        return responseModel;
    }

    private String fetchTeluguString(String inputText) throws IOException {

        String translatedText = "";   
        String inputLang = "en"; // The input language code
        String outputLang = "te"; // The output language code

                    URL url = new URL("https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + inputLang + "&tl=" + outputLang + "&dt=t&q=" + URLEncoder.encode(inputText, "UTF-8"));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),StandardCharsets.UTF_8));

                    String line;
                    StringBuffer response = new StringBuffer();
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();
        
                    // Extract the translated text from the JSON response
                    translatedText = response.toString().split("\"")[1];
        
                    System.out.println("translatedText : "+translatedText);
        
               
        return translatedText;
    }

   


    
    

    @PostMapping(value = "/mcq/answer")
    public ResponseEntity<GeneralResponse> mcqAnswer(@RequestBody TranslationRequestActivityAnswerModel requestModel) {
        
        TranslationResponseActivityAnswerModel responseModel = new TranslationResponseActivityAnswerModel();
        McqQuizReport mcqQuizReport = mcqQuizReportRepository.findById(requestModel.getQuestionSubmissionId());
        if(mcqQuizReport != null)
        {   
       
        if (mcqQuizReport.getCorrectAnswer().equals(requestModel.getOptionAnswer())) {
            responseModel.setResult(true);
        } else {
            responseModel.setResult(false);
        }
    
        
            mcqQuizReport.setGivenAnswer(requestModel.getOptionAnswer());
            mcqQuizReport.setAnswerFlag(responseModel.isResult());
            mcqQuizReportRepository.save(mcqQuizReport);
            Integer countQuestions = mcqQuizReportRepository.fetchCount(mcqQuizReport.getActivity(),mcqQuizReport.getTargetLanguage(),mcqQuizReport.getActivityCycleNumber());
            // if(mcqQuizReport.getQuestionNumber() == MCQ_COUNT)
          
            if(countQuestions == MCQ_COUNT)
           
        // if(mcqQuizReport.getQuestionNumber() == MCQ_COUNT)
        {
            mcqQuizReport.setActivityCompletedFlag(1);
            mcqQuizReportRepository.save(mcqQuizReport);
            List<McqQuizReport> report = mcqQuizReportRepository.findByActivityAndActivityCycleNumberAndTargetLanguage(2,mcqQuizReport.getActivityCycleNumber(),mcqQuizReport.getTargetLanguage());
        
            if(report != null)
            {
            List<QuizReport> quizReport = new ArrayList<QuizReport>();
            int total = 0;
            int score = 0;
            for(McqQuizReport r: report )
            {
                QuizReport q = new QuizReport();
                q.setQuestion(r.getQuestion());
                q.setAnswer(r.getCorrectAnswer());
                q.setGivenAnswer(r.getGivenAnswer());
                boolean b = r.getAnswerFlag();
                  int answerflag = b ? 1 : 0;
                if(answerflag == 1)
                    score++;
                q.setAnswerFlag(answerflag);
                quizReport.add(q);
                total++;

            }
            responseModel.setQuizReport(quizReport);
            responseModel.setScores(score);
            responseModel.setTotal(total);
            }
        }

    return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.TRUE,
    Constants.QUESTION_END, Constants.QUESTION_END_MSG, responseModel), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.FALSE,
    Constants.NO_SUBMISSION_QUESTION, Constants.NO_SUBMISSION_QUESTION_MSG, responseModel), HttpStatus.OK);
    
            }

    }
    
    public  SwahiliSentenceRequestModel generateRequestModel(SwahiliSentenceRequestModel requestModel, String srcString)
    {
        String grammarrule[] = srcString.split("\\+");
       
        if(grammarrule[0] != null && grammarrule[0].equals("NEG"))
        {
            requestModel.setFeature(grammarrule[0]);
            requestModel.setSubject(grammarrule[1]);
            requestModel.setTense(grammarrule[2]);
            requestModel.setVerb(grammarrule[3]);
        }
        else if(grammarrule[1] != null && grammarrule[1].equals("IMP"))
        {
            requestModel.setSubject(grammarrule[0]);
            requestModel.setImpcheck(grammarrule[1]);
            requestModel.setVerb(grammarrule[2]);
        }
        else{
            requestModel.setSubject(grammarrule[0]);
            requestModel.setTense(grammarrule[1]);
            requestModel.setVerb(grammarrule[2]);
        }
        McqQuizSwahili mcqQuizSwahili= mcqQuizSwahiliRepository.findByCorrectAnswer(requestModel.getVerb());
        requestModel.setEnglishVerb(mcqQuizSwahili.getInput());
        return requestModel;
        
    }

    public static String getSwahiliSentence(SwahiliSentenceRequestModel requestModel)
    {
        String finalAnswer = "";
        if(requestModel.getTense()!=null && requestModel.getTense().equals("PERF"))
        {
            finalAnswer = PERFVerbchange(requestModel.getVerb(), requestModel.getSubject(), requestModel.getFeature());
        }
        else if(requestModel.getImpcheck()!=null && requestModel.getImpcheck().equals("IMP"))
        {
            finalAnswer = IMPVerbchange(requestModel.getVerb(), requestModel.getSubject());
        }
        else if(requestModel.getFeature()!=null &&  requestModel.getFeature().equals("NEG")){
            finalAnswer = NegVerbchange(requestModel.getVerb(), requestModel.getTense(), requestModel.getSubject());
        }
        else
        {
            finalAnswer = Verbchange(requestModel.getVerb(), requestModel.getTense(), requestModel.getSubject());
        }
        return finalAnswer;
    }
   
    public static String PERFVerbchange(String verb, String subject,String feature){
			
        if(feature != null && feature.equals("NEG"))
        {
            Map<String, String> Suffixes = new HashMap<String, String>();
            Suffixes.put("1s", "si");
            Suffixes.put("2s", "hu");
            Suffixes.put("3s", "ha");
            Suffixes.put("1p", "hatu");
            Suffixes.put("2p", "ham");
            Suffixes.put("3p", "hava");
            String suffix = "";
            suffix = Suffixes.get(subject);
            suffix = suffix + "ja";
            verb = verb.substring(2);
            return suffix + verb;
        }
        else
        {
         Map<String, String> Suffixes = new HashMap<String, String>();
         Suffixes.put("1s", "ni");
         Suffixes.put("2s", "u");
         Suffixes.put("3s", "a");
         Suffixes.put("1p", "tu");
         Suffixes.put("2p", "m");
         Suffixes.put("3p", "wa");
         String suffix = "";
         suffix = Suffixes.get(subject);
         suffix = suffix + "me";
         verb = verb.substring(2);
         return suffix + verb;
        }
              
    }
    public static String IMPVerbchange(String verb, String subject){
        
        Map<String, String> Suffixes = new HashMap<String, String>();
        if(subject.equals("2s"))
        {
            if(verb.equals("kuja"))
            {
                return "njoo";
            }
            else if (verb.equals("kuleta"))
            {
                return "leta";
            }
            else{
            verb = verb.substring(2);
            verb = verb.substring(0, verb.length() - 1);
            return verb;
            }
        }
        else if(subject.equals("2p"))
        {
            if(verb.equals("kuja"))
            {
                return "njooni";
            }
            else
            {
            verb = verb.substring(2);
            verb = verb.substring(0, verb.length() - 1) + "eni";
            return verb;
            }
        }
        return verb;	
    }

    public static String NegVerbchange(String verb, String tense, String subject){
        
        Map<String, String> Suffixes = new HashMap<String, String>();
        Suffixes.put("1s", "si");
        Suffixes.put("2s", "hu");
        Suffixes.put("3s", "ha");
        Suffixes.put("1p", "hatu");
        Suffixes.put("2p", "ham");
        Suffixes.put("3p", "hava");

        String suffix = "";
        if (tense.equals("PRES")) {
            verb = verb.substring(2);
            verb = verb.substring(0, verb.length() - 1) + "i";
            suffix = Suffixes.get(subject);
        } else if (tense.equals("PAST")) {
            suffix = Suffixes.get(subject);
        } else if (tense.equals("FUT")) {
            verb = verb.substring(2);
            suffix = Suffixes.get(subject);
            suffix = suffix + "ta";
        }

        return suffix + verb;
    }
    public static int Syllablecount(String verb) {
    String[] syllables = verb.split("(?<=[aeiou])|(?=[aeiou])|(?<=m|n|ny|ng|nd|mb|nz|kw|ch|sh|ph|th)|(?=m|n|ny|ng|nd|mb|nz|kw|ch|sh|ph|th)");
   
    int syllableCount = syllables.length;

    // adjust syllable count for certain suffixes and vowel sequences
    if (verb.endsWith("ni") || verb.endsWith("si") || verb.matches(".*[aeiou]{2}.*")) {
        syllableCount--;
    }

    return syllableCount;
}
public static String Verbchange(String verb, String tense, String subject) {
    if( Syllablecount(verb)== 2)
    {
        verb = verb;
    }
    else{
    verb = verb.substring(2);
    }
    Map<String, String> Suffixes = new HashMap<String, String>();
    Suffixes.put("1s", "ni");
    Suffixes.put("2s", "u");
    Suffixes.put("3s", "a");
    Suffixes.put("1p", "tu");
    Suffixes.put("2p", "m");
    Suffixes.put("3p", "wa");

    String suffix = "";
    if (tense.equals("PRES")) {
        suffix = Suffixes.get(subject);
        suffix = suffix + "na";
    } else if (tense.equals("PAST")) {
        suffix = Suffixes.get(subject);
        suffix = suffix + "li";
    } else if (tense.equals("FUT")) {
        suffix = Suffixes.get(subject);
        suffix = suffix + "ta";
    }

    return suffix + verb;

}


    

//     @PostMapping(value = "/mcq/answer")
//     public ResponseEntity<GeneralResponse> mcqAnswerold(@RequestBody String data) {
        
//         TranslationResponseModel responseModel = gson.fromJson(data,TranslationResponseModel.class);
//         McqQuizReport mcqQuizReport = mcqQuizReportRepository.findById(responseModel.getQuestionSubmissionId());
//         if(mcqQuizReport != null)
//         {   
//         Scores scores = scoresRepository.findById(responseModel.getActivitySubmissionId());
//             if(scores != null)
//             {
//         if (mcqQuizReport.getCorrectAnswer().equals(responseModel.getOptionAnswer())) {
//             responseModel.setResult(true);
//            scores.setCorrectAnswers(scores.getCorrectAnswers()+1);
          
//            responseModel.setScores(scores.getCorrectAnswers());
//            scoresRepository.save(scores);
//         } else {
//             responseModel.setResult(false);
//         }
//          scoresRepository.save(scores);
    
        
//             mcqQuizReport.setGivenAnswer(responseModel.getOptionAnswer());
//             mcqQuizReport.setAnswerFlag(responseModel.isResult());
//             mcqQuizReportRepository.save(mcqQuizReport);
//         }
//         else{
//             return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.FALSE,
// Constants.NO_SUBMISSION_ACTIVITY, Constants.NO_SUBMISSION_ACTIVITY_MSG, responseModel), HttpStatus.OK);

//         }

//     return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.TRUE,
//     Constants.QUESTION_END, Constants.QUESTION_END_MSG, responseModel), HttpStatus.OK);
//             }
//             else{
//                 return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.FALSE,
//     Constants.NO_SUBMISSION_QUESTION, Constants.NO_SUBMISSION_QUESTION_MSG, responseModel), HttpStatus.OK);
    
//             }

//     }
     

}
