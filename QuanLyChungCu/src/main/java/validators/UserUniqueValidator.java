/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validators;

import com.qlcc.services.UserService;
import customAnnotation.UserUnique;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author DELL
 */
public class UserUniqueValidator implements ConstraintValidator<UserUnique, String> {

    private String fieldName;

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserUnique constraintAnnotation) {
        fieldName = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            switch (fieldName) {
                case "username":
                    return !userService.isUsernameExists(value);
                case "email":
                    return !userService.isEmailExists(value);
                case "phone":
                    return !userService.isPhoneExists(value);
                default:
                    return true;
            }
        } catch (NullPointerException ex) {
            return true; 
        } catch (Exception ex) {
            return false;
        }
    }

}
