/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customAnnotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import validators.EntryRightUniqueValidator;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EntryRightUniqueValidator.class)
public @interface EntryRightUnique {
    String message() default "Relative ID already exists in EntryRight";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
