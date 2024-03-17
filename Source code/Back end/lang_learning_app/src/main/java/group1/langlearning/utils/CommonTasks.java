package group1.langlearning.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class CommonTasks {
    
    
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
