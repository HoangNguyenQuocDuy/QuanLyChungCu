/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validators;

import com.qlcc.services.RoomService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import customAnnotation.RoomUnique;

/**
 *
 * @author DELL
 */
public class RoomUniqueValidator implements ConstraintValidator<RoomUnique, String> {

    @Autowired
    private RoomService roomService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        try {
            return !roomService.isRoomNameExists(value);
        } catch (NullPointerException ex) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
