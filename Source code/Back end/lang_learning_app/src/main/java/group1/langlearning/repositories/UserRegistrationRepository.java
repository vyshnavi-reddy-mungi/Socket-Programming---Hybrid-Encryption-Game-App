package group1.langlearning.repositories;

import group1.langlearning.entity.UserRegistration;

import org.springframework.data.repository.CrudRepository;

public interface UserRegistrationRepository  extends CrudRepository<UserRegistration, Integer> {
    
    UserRegistration findById(int id);

    UserRegistration findByUsername(String username);
    
    UserRegistration findByEmail(String email);

    UserRegistration findByUsernameAndPassword(String username,String password);
    
}
