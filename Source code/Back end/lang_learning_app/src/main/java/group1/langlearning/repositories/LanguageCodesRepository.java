package group1.langlearning.repositories;

import org.springframework.data.repository.CrudRepository;

import group1.langlearning.entity.LanguageCodes;

public interface LanguageCodesRepository extends CrudRepository<LanguageCodes,Integer>{
    
    LanguageCodes findByCountry(String country);
}
