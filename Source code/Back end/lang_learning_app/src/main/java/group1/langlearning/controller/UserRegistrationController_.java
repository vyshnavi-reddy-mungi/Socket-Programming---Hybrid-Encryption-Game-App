package group1.langlearning.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import group1.langlearning.entity.UserRegistration;
import group1.langlearning.models.Constants;
import group1.langlearning.models.GeneralResponse;
import group1.langlearning.models.UserRegistrationRequestModel;
import group1.langlearning.repositories.UserRegistrationRepository;
import group1.langlearning.utils.HybridEncryption;

@RestController
@RequestMapping(path="/external/api/user")
@CrossOrigin("*")
public class UserRegistrationController_ {
    
   
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    // @Autowired
    // private CommonTasks commonTasks;

    @Autowired
    private HybridEncryption hybridEncryption;

//    @Autowired
//     private RestTemplate restTemplate;

    Gson gson = new GsonBuilder().serializeNulls().create(); 


    @PostMapping(value = "/register")
    public String registerUser(@RequestBody String data) throws IOException {
        
        // ResponseEntity<GeneralResponse> generalResponse = null;
        // UserRegistrationRequestModel model = gson.fromJson(data,UserRegistrationRequestModel.class);
        GeneralResponse generalResponse = null;
        if(data!=null)
        {
            
            String decryptedData= decryptData(data);
            
        UserRegistrationRequestModel model = gson.fromJson(decryptedData,UserRegistrationRequestModel.class);
        
        if(model!=null)
        {
        UserRegistration userRegistration = userRegistrationRepository.findByUsername(model.getUsername());
        if(userRegistration != null)
        {
           generalResponse= new GeneralResponse(Constants.FALSE,
					Constants.ALREADY_REGISTERED, Constants.ALREADY_REGISTERED_MSG, null);
           return encryptData(generalResponse);         
        }

        userRegistration = userRegistrationRepository.findByEmail(model.getEmail());
        if(userRegistration != null)
        {
           generalResponse = new GeneralResponse(Constants.FALSE,
					Constants.ALREADY_REGISTERED_EMAIL, Constants.ALREADY_REGISTERED_EMAIL_MSG, null);
            return encryptData(generalResponse);
        }

        userRegistration = new UserRegistration();
        if(!checkString(model.getUsername()))
            {
           generalResponse = new GeneralResponse(Constants.FALSE,
           Constants.USERNAME_NULL, Constants.USERNAME_NULL_MSG,null);
           return encryptData(generalResponse);    
        }
        if(!checkString(model.getFirstName()))
        {
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.FIRSTNAME_NULL, Constants.FIRSTNAME_NULL_MSG, null);
        return encryptData(generalResponse);    
    }
        if(!checkString(model.getLastName()))
        {
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.LASTNAME_NULL, Constants.LASTNAME_NULL_MSG, null);
        return encryptData(generalResponse);    
    }
        if(!checkString(model.getEmail()))
        {
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.EMAIL_NULL, Constants.EMAIL_NULL_MSG, null);
        return encryptData(generalResponse);    
    }
        if(!checkString(model.getNewPassword()))
        {
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.NEW_PASSWORD_NULL, Constants.NEW_PASSWORD_NULL_MSG, null);
        return encryptData(generalResponse);    
    }
        if(!checkString(model.getConfirmPassword()))
        {
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.CONFIRM_PASSWORD_NULL, Constants.CONFIRM_PASSWORD_NULL_MSG, null);
        return encryptData(generalResponse);    
    }
        if(!(model.getNewPassword().equals(model.getConfirmPassword())))
        {   
            generalResponse = new GeneralResponse(Constants.FALSE,
            Constants.PASSWORD_MISMATCH, Constants.PASSWORD_MISMATCH_MSG, null);
            return encryptData(generalResponse);
        }
        if(!checkPassword(model.getNewPassword()))
        {
            generalResponse = new GeneralResponse(Constants.FALSE,
            Constants.MISSING_PASSWORD_FORMAT, Constants.MISSING_PASSWORD_FORMAT_MSG, null);
            return encryptData(generalResponse);
        }
         
        userRegistration.setUsername(model.getUsername());
        userRegistration.setFirstName(model.getFirstName());
        userRegistration.setLastName(model.getLastName());
        userRegistration.setEmail(model.getEmail());
        userRegistration.setNewPassword(model.getNewPassword());
        userRegistration.setConfirmPassword(model.getConfirmPassword());
        userRegistration.setCreatedTimestamp(new Date());
        userRegistration.setPassword(model.getConfirmPassword());
        userRegistrationRepository.save(userRegistration);
        
       }  
       
       
        }
        else
       {
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG,null);
        return encryptData(generalResponse);   
    }
       generalResponse = new GeneralResponse(Constants.TRUE,
       Constants.USER_REGISTERED_SUCCESSFULLY, Constants.USER_REGISTERED_SUCCESSFULLY_MSG, null);
       return encryptData(generalResponse);

    
    }

private String encryptData(GeneralResponse generalResponse) {
    String encryptData = gson.toJson(generalResponse,GeneralResponse.class);         
         
        // TODO Auto-generated method stub
        String encryptedData= "";
        try {
            encryptedData = hybridEncryption.encryptData(encryptData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedData;
    }

    private String decryptData(String data) {
        // TODO Auto-generated method stub
        String decryptedData= "";
        try {
            decryptedData = hybridEncryption.DecryptData(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return decryptedData;
    }


@PostMapping(value = "/login")
public String loginUser(@RequestBody String data) throws IOException 
{
    
    // ResponseEntity<GeneralResponse> generalResponse = null;
    GeneralResponse generalResponse = null;
    if(data!=null)
    {
        String decryptedData= decryptData(data);
        UserRegistrationRequestModel model = gson.fromJson(decryptedData,UserRegistrationRequestModel.class);

    UserRegistration userRegistration = userRegistrationRepository.findByUsernameAndPassword(model.getUsername(),model.getPassword());
    if(userRegistration != null)
    {
      generalResponse = new GeneralResponse(Constants.TRUE,
                Constants.LOGIN_SUCCESSFUL, Constants.LOGIN_SUCCESSFUL_MSG, null);
    return encryptData(generalResponse);
    }

     userRegistration = userRegistrationRepository.findByUsername(model.getUsername());
    if(userRegistration != null)
    {
       generalResponse = new GeneralResponse(Constants.FALSE,
                Constants.INCORRECT_PASSWORD, Constants.INCORRECT_PASSWORDL_MSG, null);
        return encryptData(generalResponse);
    }

    else{
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.USER_NOT_FOUND, Constants.USER_NOT_FOUND_MSG, null);
        return encryptData(generalResponse);
    
    }
    }
    else
    {
     generalResponse = new GeneralResponse(Constants.FALSE,
     Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG, null);
     return encryptData(generalResponse);
    }
   
}


@PostMapping(value = "/forgot/password")
public String forgotPassword(@RequestBody String data) throws IOException {
    
    // ResponseEntity<GeneralResponse> generalResponse = null;
    String decryptedData = decryptData(data);
    UserRegistrationRequestModel model = gson.fromJson(decryptedData,UserRegistrationRequestModel.class);
    decryptedData = gson.toJson(model,UserRegistrationRequestModel.class);

    GeneralResponse generalResponse = null;
    if(data!=null)
    {

    UserRegistration userRegistration = userRegistrationRepository.findByUsername(model.getUsername());
    if(userRegistration != null)
    {
        if(!checkString(model.getNewPassword()))
        {
        generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.NEW_PASSWORD_NULL, Constants.NEW_PASSWORD_NULL_MSG, null);
        return encryptData(generalResponse);
        }
        if(!checkString(model.getConfirmPassword()))
        {
       generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.CONFIRM_PASSWORD_NULL, Constants.CONFIRM_PASSWORD_NULL_MSG, null);
        return encryptData(generalResponse);
        }
        if(!(model.getNewPassword().equals(model.getConfirmPassword())))
        {   
            generalResponse = new GeneralResponse(Constants.FALSE,
            Constants.PASSWORD_MISMATCH, Constants.PASSWORD_MISMATCH_MSG, null);
            return encryptData(generalResponse);
        }
        if(!checkPassword(model.getNewPassword()))
        {
            generalResponse = new GeneralResponse(Constants.FALSE,
            Constants.MISSING_PASSWORD_FORMAT, Constants.MISSING_PASSWORD_FORMAT_MSG, null);
            return encryptData(generalResponse);
        }

        userRegistration.setNewPassword(model.getNewPassword());
        userRegistration.setConfirmPassword(model.getConfirmPassword());
        userRegistration.setPassword(model.getConfirmPassword());
        userRegistration.setUpdatedTimestamp(new Date());
        userRegistrationRepository.save(userRegistration);

       generalResponse = new GeneralResponse(Constants.TRUE,
                Constants.PASSWORD_CHANGED_SUCCESSFULLY, Constants.PASSWORD_CHANGED_SUCCESSFULLY_MSG, null);
                return encryptData(generalResponse);
    }

    else{
       generalResponse = new GeneralResponse(Constants.FALSE,
        Constants.USER_NOT_FOUND, Constants.USER_NOT_FOUND_MSG, null);
        return encryptData(generalResponse);
    
    }
    }
    else
    {
     generalResponse = new GeneralResponse(Constants.FALSE,
     Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG, null);
     return encryptData(generalResponse);
    }

     }


     @PostMapping(value = "/forgot/username")
     public String forgotUsername(@RequestBody String data) throws IOException {
         
         // ResponseEntity<GeneralResponse> generalResponse = null;
         String decryptedData = decryptData(data);
         GeneralResponse generalResponse = null;
         UserRegistrationRequestModel model = gson.fromJson(decryptedData,UserRegistrationRequestModel.class);
         data = gson.toJson(model,UserRegistrationRequestModel.class);
         
         if(data!=null)
         {
     
         UserRegistration userRegistration = userRegistrationRepository.findByEmail(model.getEmail());
         if(userRegistration != null)
         {
             String userName = userRegistration.getUsername();
     
     
            generalResponse = new GeneralResponse(Constants.TRUE,
                     Constants.USER_NAME_FOUND, Constants.USER_NAME_FOUND_MSG, userName);
                     return encryptData(generalResponse);
         }
     
         else{
             generalResponse = new GeneralResponse(Constants.FALSE,
             Constants.USER_EMAIL_NOT_FOUND, Constants.USER_EMAIL_NOT_FOUND_MSG, null);
             return encryptData(generalResponse);
         }
         }
         else
         {
          generalResponse = new GeneralResponse(Constants.FALSE,
          Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG, null);
          return encryptData(generalResponse);
         }
    }

          
public boolean checkString(String... data)
{
    if(data == null)
        return false;
    for(String str:data)
    {
        if(str == null)
        return false;
    }
    return true;
}

public boolean checkPassword(String password) {
    String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
}
}
