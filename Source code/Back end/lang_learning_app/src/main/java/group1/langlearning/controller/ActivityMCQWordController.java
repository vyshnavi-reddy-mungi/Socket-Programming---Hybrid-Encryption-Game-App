package group1.langlearning.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group1.langlearning.repositories.McqQuizReportRepository;
import group1.langlearning.repositories.McqQuizRepository;
import group1.langlearning.repositories.McqQuizSwahiliRepository;
import group1.langlearning.entity.McqQuiz;
import group1.langlearning.entity.McqQuizReport;
import group1.langlearning.entity.McqQuizSwahili;
import group1.langlearning.models.Constants;
import group1.langlearning.models.GeneralResponse;
import group1.langlearning.models.QuizReport;
import group1.langlearning.models.TranslationRequestActivity;
import group1.langlearning.models.TranslationRequestActivityAnswerModel;
import group1.langlearning.models.TranslationResponseActivity;
import group1.langlearning.models.TranslationResponseActivityAnswerModel;
import group1.langlearning.models.TranslationResponseModel;

@RestController
@RequestMapping(path = "/activity1")
@CrossOrigin("*")
public class ActivityMCQWordController {
    
    Gson gson = new GsonBuilder().create(); 

   
    private McqQuizReportRepository mcqQuizReportRepository;

    @Autowired
    public ActivityMCQWordController(McqQuizReportRepository mcqQuizReportRepository) {
        this.mcqQuizReportRepository = mcqQuizReportRepository;
    }


    @Autowired
    private McqQuizSwahiliRepository mcqQuizSwahiliRepository;

    @Autowired
    private McqQuizRepository mcqQuizRepository;

    private final int MCQ_COUNT = 2;
    // private final int TOTAL_GAME_ACTIVITY_QUESTIONS_TO_LEARN = 3;

    private final int TOTAL_GAME_ACTIVITY_QUESTIONS_TELUGU = 40;

    private final int TOTAL_GAME_ACTIVITY_QUESTIONS_SWAHILI = 172;

     
    
    @PostMapping(value = "/mcq/start")
    public ResponseEntity<GeneralResponse> mcqStart(@RequestBody TranslationRequestActivity requestModel) {
        
        TranslationResponseActivity responseModel = new TranslationResponseActivity();
        Random random = new Random();
        int TOTAL_GAME_ACTIVITY_QUESTIONS = 0;
        if(requestModel.getTargetLanguage()!= null && requestModel.getTargetLanguage().equals("te"))
            TOTAL_GAME_ACTIVITY_QUESTIONS = TOTAL_GAME_ACTIVITY_QUESTIONS_TELUGU;
        else if(requestModel.getTargetLanguage()!= null && requestModel.getTargetLanguage().equals("sw"))
            TOTAL_GAME_ACTIVITY_QUESTIONS = TOTAL_GAME_ACTIVITY_QUESTIONS_SWAHILI;
        else
            TOTAL_GAME_ACTIVITY_QUESTIONS = 10;

        McqQuizReport mcqQuizReport = mcqQuizReportRepository.fetchUsername(1,requestModel.getUsername(),requestModel.getTargetLanguage());
        McqQuizReport mcqQuizReportNew = null;
        if(mcqQuizReport != null)
        {
            if(mcqQuizReport.getGivenAnswer() != null)
            {
            mcqQuizReportNew = new McqQuizReport();
            if(mcqQuizReport.getActivityCompletedFlag() != null && mcqQuizReport.getActivityCompletedFlag()== 1)
            {
              mcqQuizReportNew.setActivityCycleNumber(mcqQuizReport.getActivityCycleNumber() + 1);
              int randomNumber = random.nextInt(TOTAL_GAME_ACTIVITY_QUESTIONS) + 1;
              mcqQuizReportNew.setQuestionNumber(randomNumber);
                System.out.println("randomNumber : "+randomNumber);
            }
           
            mcqQuizReportNew.setUsername(requestModel.getUsername());
            mcqQuizReportNew.setCreatedTimestamp(new Date());
            mcqQuizReportNew.setActivity(1);
            if(mcqQuizReportNew.getQuestionNumber() == null)
            {
                // mcqQuizReportNew.setQuestionNumber(mcqQuizReport.getQuestionNumber() + 1);
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
           
            // responseModel.setQuestionNo(mcqQuizReportNew.getQuestionNumber());
            mcqQuizReportRepository.save(mcqQuizReportNew);
            Integer countQuestions = mcqQuizReportRepository.fetchCount(mcqQuizReportNew.getActivity(),requestModel.getTargetLanguage(),mcqQuizReportNew.getActivityCycleNumber());
            responseModel.setQuestionNo(countQuestions + 1);

            if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("te"))
            {
            McqQuiz mcq = mcqQuizRepository.findById((int)mcqQuizReportNew.getQuestionNumber());
            if(mcq != null)
            {
                responseModel = fetchMCQTeluguOptions(responseModel, mcq, mcqQuizReportNew, requestModel.getTargetLanguage());
            }
            }
            else if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("sw"))
            {
                
            McqQuizSwahili mcqSwahili = mcqQuizSwahiliRepository.findById((int)mcqQuizReportNew.getQuestionNumber());
            if(mcqSwahili != null)
            {
                responseModel = fetchMCQSwahiliOptions(responseModel, mcqSwahili, mcqQuizReportNew, requestModel.getTargetLanguage());
            }
            }

            }
            else{

                // responseModel.setQuestionNo(mcqQuizReport.getQuestionNumber());
                mcqQuizReportRepository.save(mcqQuizReport);
            

                if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("te"))
                {
                McqQuiz mcq = mcqQuizRepository.findById((int)mcqQuizReport.getQuestionNumber());
                if(mcq != null)
                {
                    responseModel = fetchMCQTeluguOptions(responseModel, mcq, mcqQuizReport, requestModel.getTargetLanguage());
                }
                }
                else if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("sw"))
                {
                    
                McqQuizSwahili mcqSwahili = mcqQuizSwahiliRepository.findById((int)mcqQuizReport.getQuestionNumber());
                if(mcqSwahili != null)
                {
                    responseModel = fetchMCQSwahiliOptions(responseModel, mcqSwahili, mcqQuizReport, requestModel.getTargetLanguage());
                }
                }
                Integer countQuestions = mcqQuizReportRepository.fetchCount(mcqQuizReport.getActivity(),requestModel.getTargetLanguage(),mcqQuizReport.getActivityCycleNumber());
            responseModel.setQuestionNo(countQuestions );
            }
        }
        else{
            mcqQuizReport = new McqQuizReport();
            mcqQuizReport.setUsername(requestModel.getUsername());
            mcqQuizReport.setCreatedTimestamp(new Date());
            mcqQuizReport.setActivity(1);
            int randomNumber = random.nextInt(TOTAL_GAME_ACTIVITY_QUESTIONS) + 1;
            mcqQuizReport.setQuestionNumber(randomNumber);
            // mcqQuizReport.setQuestionNumber(1);
            mcqQuizReport.setActivityCycleNumber(1);

            // responseModel.setQuestionNo(mcqQuizReport.getQuestionNumber());
            mcqQuizReportRepository.save(mcqQuizReport);
            Integer countQuestions = mcqQuizReportRepository.fetchCount(mcqQuizReport.getActivity(),requestModel.getTargetLanguage(),mcqQuizReport.getActivityCycleNumber());
            responseModel.setQuestionNo(countQuestions + 1);
           
            if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("te"))
            {
            McqQuiz mcq = mcqQuizRepository.findById((int)mcqQuizReport.getQuestionNumber());
            if(mcq != null)
            {
                responseModel = fetchMCQTeluguOptions(responseModel, mcq, mcqQuizReport, requestModel.getTargetLanguage());
            }
            }
            else if(requestModel.getTargetLanguage() != null && requestModel.getTargetLanguage().equals("sw"))
            {
                
            McqQuizSwahili mcqSwahili = mcqQuizSwahiliRepository.findById((int)mcqQuizReport.getQuestionNumber());
            if(mcqSwahili != null)
            {
                responseModel = fetchMCQSwahiliOptions(responseModel, mcqSwahili, mcqQuizReport, requestModel.getTargetLanguage());
            }
            }

        }
        System.out.println("model " + gson.toJson(responseModel));
    
            responseModel.setTotalQuestions(MCQ_COUNT);
            return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.TRUE,
        Constants.OPTIONS_SENT, Constants.OPTIONS_SENT, responseModel), HttpStatus.OK);
      
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
        {
            mcqQuizReport.setActivityCompletedFlag(1);
            mcqQuizReportRepository.save(mcqQuizReport);
            List<McqQuizReport> report = mcqQuizReportRepository.findByActivityAndActivityCycleNumberAndTargetLanguage(1,mcqQuizReport.getActivityCycleNumber(),mcqQuizReport.getTargetLanguage());
        
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

              
    private TranslationResponseActivity fetchMCQTeluguOptions(TranslationResponseActivity responseModel, McqQuiz mcq, McqQuizReport mcqQuizReportNew,String targetLanguage)
    {
        responseModel.setInputText(mcq.getInput());
                responseModel.setCorrectAnswer(mcq.getCorrectAnswer());
                String options[] = mcq.getOptions().split(",");
                Collections.shuffle(Arrays.asList(options));
                responseModel.setMcqOptions(options);

                mcqQuizReportNew.setQuestion(responseModel.getInputText());
                String optionsGiven = "";
                for(String s:responseModel.getMcqOptions())
                     optionsGiven = optionsGiven + s + ",";
                mcqQuizReportNew.setOptions(optionsGiven.substring(0, optionsGiven.length()-1));
                // mcqQuizReportNew.setOptions(model.getMcqOptions().toString());
                mcqQuizReportNew.setCorrectAnswer(responseModel.getCorrectAnswer());
                mcqQuizReportNew.setTargetLanguage(targetLanguage);
                mcqQuizReportRepository.save(mcqQuizReportNew);
                responseModel.setQuestionSubmissionId(mcqQuizReportNew.getId());

                return responseModel;
    }

    private TranslationResponseActivity fetchMCQSwahiliOptions(TranslationResponseActivity responseModel, McqQuizSwahili mcqSwahili, McqQuizReport mcqQuizReportNew, String targetLanguage)
    {
        responseModel.setInputText(mcqSwahili.getInput());
                responseModel.setCorrectAnswer(mcqSwahili.getCorrectAnswer());
                String options[] = mcqSwahili.getOptions().split(",");
                Collections.shuffle(Arrays.asList(options));
                responseModel.setMcqOptions(options);

                mcqQuizReportNew.setQuestion(responseModel.getInputText());
                String optionsGiven = "";
                for(String s:responseModel.getMcqOptions())
                     optionsGiven = optionsGiven + s + ",";
                mcqQuizReportNew.setOptions(optionsGiven.substring(0, optionsGiven.length()-1));
                // mcqQuizReportNew.setOptions(model.getMcqOptions().toString());
                mcqQuizReportNew.setCorrectAnswer(responseModel.getCorrectAnswer());
                mcqQuizReportNew.setTargetLanguage(targetLanguage);
                mcqQuizReportRepository.save(mcqQuizReportNew);
                responseModel.setQuestionSubmissionId(mcqQuizReportNew.getId());

                return responseModel;
    }
     
}
