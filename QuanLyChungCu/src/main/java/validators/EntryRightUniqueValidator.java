/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validators;

import com.qlcc.services.EntryRightService;
import customAnnotation.EntryRightUnique;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author DELL
 */
public class EntryRightUniqueValidator implements ConstraintValidator<EntryRightUnique, Integer> {
    @Autowired
    private EntryRightService entryRightService;

    @Override
    public void initialize(EntryRightUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer relativeId, ConstraintValidatorContext context) {
        if (relativeId == null) {
            return true;
        }

        return !entryRightService.isEntryRightRelativeExitsts(relativeId);
    }
}
