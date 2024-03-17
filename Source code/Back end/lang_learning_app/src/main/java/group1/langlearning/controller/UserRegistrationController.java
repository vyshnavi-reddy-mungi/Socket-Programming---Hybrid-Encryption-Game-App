package group1.langlearning.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group1.langlearning.entity.UserRegistration;
import group1.langlearning.models.Constants;
import group1.langlearning.models.GeneralResponse;
import group1.langlearning.models.UserRegistrationRequestModel;
import group1.langlearning.repositories.UserRegistrationRepository;
import group1.langlearning.utils.HybridEncryption;

@RestController
@RequestMapping(path="/api/user")
@CrossOrigin("*")
public class UserRegistrationController {
    
   
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
    public ResponseEntity<GeneralResponse> registerUser(@RequestBody String data) throws IOException {
        
        // ResponseEntity<GeneralResponse> generalResponse = null;
        // UserRegistrationRequestModel model = gson.fromJson(data,UserRegistrationRequestModel.class);
    
        if(data!=null)
        {
            
            String encryptedData= "";
        try {
            encryptedData = hybridEncryption.encryptData(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        String url = "http://localhost:8082/external/api/user/register";
        String decryptedData = postData(encryptedData, url);
   
    // ResponseEntity<GeneralResponse> generalResponse = null;
    // System.out.println("Decrypted data : "+decryptedData);
    
        GeneralResponse model = gson.fromJson(decryptedData,GeneralResponse.class);
    
        return new ResponseEntity<GeneralResponse>(model, HttpStatus.OK);
        
        }
        else
        {
         return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.FALSE,
         Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG, null), HttpStatus.OK);
        }
    
    
    }
    

@PostMapping(value = "/login")
public ResponseEntity<GeneralResponse> loginUser(@RequestBody String data) throws IOException {
    
    UserRegistrationRequestModel loginModel = gson.fromJson(data,UserRegistrationRequestModel.class);
    data = gson.toJson(loginModel,UserRegistrationRequestModel.class);
    if(data!=null)
    {
        String encryptedData= "";
    try {
        encryptedData = hybridEncryption.encryptData(data);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    String url = "http://localhost:8082/external/api/user/login";
    String decryptedData = postData(encryptedData, url);

// ResponseEntity<GeneralResponse> generalResponse = null;
// System.out.println("Decrypted data : "+decryptedData);

    GeneralResponse model = gson.fromJson(decryptedData,GeneralResponse.class);

    return new ResponseEntity<GeneralResponse>(model, HttpStatus.OK);
    
    }
    else
    {
     return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.FALSE,
     Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG, null), HttpStatus.OK);
    }
   
}


@PostMapping(value = "/forgot/password")
public ResponseEntity<GeneralResponse> forgotPassword(@RequestBody String data) throws IOException {
    
    // ResponseEntity<GeneralResponse> generalResponse = null;
    UserRegistrationRequestModel forgotPasswordModel = gson.fromJson(data,UserRegistrationRequestModel.class);
    data = gson.toJson(forgotPasswordModel,UserRegistrationRequestModel.class);
    if(data!=null)
    {
       
        String encryptedData= "";
        try {
            encryptedData = hybridEncryption.encryptData(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        String url = "http://localhost:8082/external/api/user/forgot/password";
        String decryptedData = postData(encryptedData, url);
    
     // ResponseEntity<GeneralResponse> generalResponse = null;
     // System.out.println("Decrypted data : "+decryptedData);
     
         GeneralResponse model = gson.fromJson(decryptedData,GeneralResponse.class);
     
         return new ResponseEntity<GeneralResponse>(model, HttpStatus.OK);
         
         }
         else
         {
          return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.FALSE,
          Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG, null), HttpStatus.OK);
         }
     
     
     }


     @PostMapping(value = "/forgot/username")
     public ResponseEntity<GeneralResponse> forgotUsername(@RequestBody String data) throws IOException {
         
         // ResponseEntity<GeneralResponse> generalResponse = null;
         UserRegistrationRequestModel forgotUsername = gson.fromJson(data,UserRegistrationRequestModel.class);
         data = gson.toJson(forgotUsername,UserRegistrationRequestModel.class);
         
         if(data!=null)
         {
             String encryptedData= "";
             try {
                 encryptedData = hybridEncryption.encryptData(data);
             } catch (Exception e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             String url = "http://localhost:8082/external/api/user/forgot/password";
             String decryptedData = postData(encryptedData, url);
        
          // ResponseEntity<GeneralResponse> generalResponse = null;
          // System.out.println("Decrypted data : "+decryptedData);
          
              GeneralResponse model = gson.fromJson(decryptedData,GeneralResponse.class);
          
              return new ResponseEntity<GeneralResponse>(model, HttpStatus.OK);
              
              }
              else
              {
               return new ResponseEntity<GeneralResponse>(new GeneralResponse(Constants.FALSE,
               Constants.REQUEST_DATA_MISSING, Constants.REQUEST_DATA_MISSING_MSG, null), HttpStatus.OK);
              }
          
          
          }

        public String  postData(String encryptedData, String postUrl) throws IOException
          {
              URL url = new URL(postUrl);
              HttpURLConnection connection = (HttpURLConnection) url.openConnection();
              
              connection.setRequestMethod("POST");
              connection.setRequestProperty("Content-Type", "application/json"); // Set content type
              connection.setConnectTimeout(60000);
              
              // Prepare your data as a String
              // String data = "{\"name\": \"John Doe\", \"age\": 30}";
              
              connection.setDoOutput(true); // Allow writing to output stream
              OutputStream os = connection.getOutputStream();
              os.write(encryptedData.getBytes()); // Write data to output stream
              os.flush();
              
              
              int responseCode = connection.getResponseCode();
              String content = "";
              if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                InputStream is = connection.getInputStream();
                
                StringBuilder sb = new StringBuilder();
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                
                while ((line = reader.readLine()) != null) {
                  sb.append(line); // Add newline character after each line
                }
                
                 content = sb.toString();
                
                reader.close();
               
                is.close();
              } else {
                System.out.println("Error:"+responseCode);
              }
              
              connection.disconnect();
              
              // System.out.println("content-----------:"+content);
              String decryptedData= "";
              try {
                  decryptedData = hybridEncryption.DecryptData(content);
              } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
            return decryptedData;
          }
      
}
