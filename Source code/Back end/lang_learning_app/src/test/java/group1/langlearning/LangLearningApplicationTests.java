package group1.langlearning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group1.langlearning.controller.UserRegistrationController;
import group1.langlearning.entity.McqQuizReport;
import group1.langlearning.entity.UserRegistration;
import group1.langlearning.models.Constants;
import group1.langlearning.models.GeneralResponse;
import group1.langlearning.models.TranslationRequestActivity;
import group1.langlearning.models.UserRegistrationRequestModel;
import group1.langlearning.repositories.McqQuizReportRepository;
import group1.langlearning.repositories.UserRegistrationRepository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(UserRegistrationController.class)
public class LangLearningApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegistrationRepository userRegistrationRepository;

    @MockBean
    private McqQuizReportRepository mcqQuizReportRepository;

    private final String USER_REGISTRATION_API = "/api/user/register";
    private final String USER_LOGIN_API = "/api/user/login";
    private final String USER_FORGOT_PASSWORD_API = "/api/user/forgot/password";
    private final String USER_FORGOT_USERNAME = "/api/user/forgot/username";

    private final String ACTIVITY1_QUESTION = "/activity1/mcq/start";
    private final String ACTIVITY1_ANSWER = "/activity1/mcq/answer";
    private final String ACTIVITY2_QUESTION = "/activity2/new/mcq/start";
    private final String ACTIVITY2_ANSWER = "/activity2/new/mcq/answer";


    Gson gson = new GsonBuilder().serializeNulls().create();

    
    
	@Test
    public void testMCQActivity_1_Questions() throws Exception  {

        String username = "vyshnavireddy";
        String targetLanguage = "te";
             
        TranslationRequestActivity requestModel = new TranslationRequestActivity(); 
        requestModel.setUsername(username);
        requestModel.setTargetLanguage(targetLanguage);
       
        McqQuizReport mcqQuizReport = mcqQuizReportRepository.fetchUsername(1,username,targetLanguage);
       
        Mockito.when(mcqQuizReportRepository.fetchUsername(1,username,targetLanguage)).thenReturn(null);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ACTIVITY1_QUESTION)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        System.out.println("general response : "+content);

        // assertEquals(200, statusCode);
        // assertEquals(Constants.FALSE, response.isStatus());
        // assertEquals(Constants.USERNAME_NULL, response.getResponseCode());
        // assertEquals(Constants.USERNAME_NULL_MSG, response.getResponseMessage());
    }



    @Test
    public void testUserRegisterSuccess() throws Exception  {
        String firstName = "Vyshnavi";
        String lastName = "Reddy";
        String userName = "vyshnavireddy9";
        String email = "vyshreddy9@mail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.TRUE, response.isStatus());
        assertEquals(Constants.USER_REGISTERED_SUCCESSFULLY, response.getResponseCode());
        assertEquals(Constants.USER_REGISTERED_SUCCESSFULLY_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserAlreadyRegistered() throws Exception  {
        String firstName = "Vyshnavi";
        String lastName = "Reddy";
        String userName = "vyshnavireddy9";
        String email = "vyshreddy9@mail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(new UserRegistration());
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.ALREADY_REGISTERED, response.getResponseCode());
        assertEquals(Constants.ALREADY_REGISTERED_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserEmailAlreadyRegistered() throws Exception  {
        String firstName = "Vyshnavi";
        String lastName = "Reddy";
        String userName = "vyshnavireddy9";
        String email = "vyshreddy9@mail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByEmail(email)).thenReturn(new UserRegistration());
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.ALREADY_REGISTERED_EMAIL, response.getResponseCode());
        assertEquals(Constants.ALREADY_REGISTERED_EMAIL_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserUsernameNull() throws Exception  {
        String firstName = "Vyshnavi";
        String lastName = "Reddy";
        String userName = null;
        String email = "vyshreddy9@mail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.USERNAME_NULL, response.getResponseCode());
        assertEquals(Constants.USERNAME_NULL_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserFirstnameNull() throws Exception  {
        String firstName = null;
        String lastName = "Reddy";
        String userName = "Vysh";
        String email = "vyshreddy9@mail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.FIRSTNAME_NULL, response.getResponseCode());
        assertEquals(Constants.FIRSTNAME_NULL_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserLastnameNull() throws Exception  {
        String firstName = "Vyshnavi";
        String lastName = null;
        String userName = "Vysh";
        String email = "vyshreddy9@mail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.LASTNAME_NULL, response.getResponseCode());
        assertEquals(Constants.LASTNAME_NULL_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserEmailNull() throws Exception  {
        String firstName = "Vysh";
        String lastName = "Reddy";
        String userName = "Vysh";
        String email = null;
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.EMAIL_NULL, response.getResponseCode());
        assertEquals(Constants.EMAIL_NULL_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserNewPasswordNull() throws Exception  {
        String firstName = "Vysh";
        String lastName = "Reddy";
        String userName = "Vysh";
        String email = "vyshreddy9@gmail.com";
        String newPassword = null;
        String confirmPassword = "Vyshnavi99$";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.NEW_PASSWORD_NULL, response.getResponseCode());
        assertEquals(Constants.NEW_PASSWORD_NULL_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserConfirmPasswordNull() throws Exception  {
        String firstName = "Vysh";
        String lastName = "Reddy";
        String userName = "Vysh";
        String email = "vyshreddy9@gmail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = null;
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.CONFIRM_PASSWORD_NULL, response.getResponseCode());
        assertEquals(Constants.CONFIRM_PASSWORD_NULL_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserPasswordMismatch() throws Exception  {
        String firstName = "Vysh";
        String lastName = "Reddy";
        String userName = "Vysh";
        String email = "vyshreddy9@gmail.com";
        String newPassword = "Vyshnavi99$";
        String confirmPassword = "Vyshnavi99$00";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.PASSWORD_MISMATCH, response.getResponseCode());
        assertEquals(Constants.PASSWORD_MISMATCH_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserMissingPasswordFormat() throws Exception  {
        String firstName = "Vysh";
        String lastName = "Reddy";
        String userName = "Vysh";
        String email = "vyshreddy9@gmail.com";
        String newPassword = "Vyshv";
        String confirmPassword = "Vyshv";
       
        UserRegistration user =  new UserRegistration();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(userName);
        user.setEmail(email);
        user.setNewPassword(newPassword);
        user.setConfirmPassword(confirmPassword);

        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(user);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setUsername(userName);
        requestModel.setEmail(email);
        requestModel.setNewPassword(newPassword);
        requestModel.setConfirmPassword(confirmPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REGISTRATION_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.MISSING_PASSWORD_FORMAT, response.getResponseCode());
        assertEquals(Constants.MISSING_PASSWORD_FORMAT_MSG, response.getResponseMessage());
    }


	@Test
    public void testUserLoginSuccess() throws Exception  {
     
        String userName = "vyshnavireddy9";
        String password = "Vyshnavi99$";
       
        Mockito.when(userRegistrationRepository.findByUsernameAndPassword(userName,password)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
        requestModel.setPassword(password);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_LOGIN_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.TRUE, response.isStatus());
        assertEquals(Constants.LOGIN_SUCCESSFUL, response.getResponseCode());
        assertEquals(Constants.LOGIN_SUCCESSFUL_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserLoginWrongPassword() throws Exception  {
     
        String userName = "vyshnavireddy9";
        String password = "Vyshnavi99$$";
       
      Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
		requestModel.setPassword(password);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_LOGIN_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        PrintWriter writer = new PrintWriter("Output.txt","UTF-8");
		writer.println("response : "+content);
		writer.close();
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.INCORRECT_PASSWORD, response.getResponseCode());
        assertEquals(Constants.INCORRECT_PASSWORDL_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserLoginUserNotFound() throws Exception  {
     
        String userName = "vyshnavireddy900";
        String password = "Vyshnavi99$$";
       
      Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(null);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
		requestModel.setPassword(password);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_LOGIN_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        PrintWriter writer = new PrintWriter("Output.txt","UTF-8");
		writer.println("response : "+content);
		writer.close();
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.USER_NOT_FOUND, response.getResponseCode());
        assertEquals(Constants.USER_NOT_FOUND_MSG, response.getResponseMessage());
    }


	@Test
    public void testUserForgotPasswordSuccess() throws Exception  {
     
        String userName = "vyshnavireddy9";
        String confirmPassword = "Vyshnavi99$$";
		String newPassword = "Vyshnavi99$$";
       
        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
		requestModel.setConfirmPassword(confirmPassword);
		requestModel.setNewPassword(newPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_FORGOT_PASSWORD_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.TRUE, response.isStatus());
        assertEquals(Constants.PASSWORD_CHANGED_SUCCESSFULLY, response.getResponseCode());
        assertEquals(Constants.PASSWORD_CHANGED_SUCCESSFULLY_MSG, response.getResponseMessage());
    }

	
	@Test
    public void testUserForgotPasswordNewPasswordNull() throws Exception  {
     
        String userName = "vyshnavireddy9";
        String confirmPassword = "Vyshnavi99$$";
		String newPassword = null;
       
        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
		requestModel.setConfirmPassword(confirmPassword);
		requestModel.setNewPassword(newPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_FORGOT_PASSWORD_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.NEW_PASSWORD_NULL, response.getResponseCode());
        assertEquals(Constants.NEW_PASSWORD_NULL_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserForgotPasswordConfirmPasswordNull() throws Exception  {
     
        String userName = "vyshnavireddy9";
        String confirmPassword = null;
		String newPassword = "Vyshnavi99$$";
       
        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
		requestModel.setConfirmPassword(confirmPassword);
		requestModel.setNewPassword(newPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_FORGOT_PASSWORD_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.CONFIRM_PASSWORD_NULL, response.getResponseCode());
        assertEquals(Constants.CONFIRM_PASSWORD_NULL_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserForgotPasswordMismatch() throws Exception  {
     
        String userName = "vyshnavireddy9";
        String confirmPassword = "Vyshnavi99$$";
		String newPassword = "Vyshnavi99$$00";
       
        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
		requestModel.setConfirmPassword(confirmPassword);
		requestModel.setNewPassword(newPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_FORGOT_PASSWORD_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.PASSWORD_MISMATCH, response.getResponseCode());
        assertEquals(Constants.PASSWORD_MISMATCH_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserForgotPasswordWrongFormat() throws Exception  {
     
        String userName = "vyshnavireddy9";
        String confirmPassword = "Vyshnavi99";
		String newPassword = "Vyshnavi99";
       
        Mockito.when(userRegistrationRepository.findByUsername(userName)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setUsername(userName);
		requestModel.setConfirmPassword(confirmPassword);
		requestModel.setNewPassword(newPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_FORGOT_PASSWORD_API)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.MISSING_PASSWORD_FORMAT, response.getResponseCode());
        assertEquals(Constants.MISSING_PASSWORD_FORMAT_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserForgotUserNameSuccess() throws Exception  {
     
        String email = "vyshreddy9@gmail.com";
      
        Mockito.when(userRegistrationRepository.findByEmail(email)).thenReturn(new UserRegistration());

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setEmail(email);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_FORGOT_USERNAME)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.TRUE, response.isStatus());
        assertEquals(Constants.USER_NAME_FOUND, response.getResponseCode());
        assertEquals(Constants.USER_NAME_FOUND_MSG, response.getResponseMessage());
    }

	@Test
    public void testUserForgotUserNameNotFound() throws Exception  {
     
        String email = "vysh9@gmail.com";
      
        Mockito.when(userRegistrationRepository.findByEmail(email)).thenReturn(null);

        UserRegistrationRequestModel requestModel = new UserRegistrationRequestModel();
		requestModel.setEmail(email);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_FORGOT_USERNAME)
                .content(asJsonString(requestModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
       
        GeneralResponse response = gson.fromJson(content, GeneralResponse.class);
        
        assertEquals(200, statusCode);
        assertEquals(Constants.FALSE, response.isStatus());
        assertEquals(Constants.USER_EMAIL_NOT_FOUND, response.getResponseCode());
        assertEquals(Constants.USER_EMAIL_NOT_FOUND_MSG, response.getResponseMessage());
    }

	private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonContent = objectMapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

}
