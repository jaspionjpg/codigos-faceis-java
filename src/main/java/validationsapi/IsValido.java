package validationsapi;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsValidoValidator.class)
@Documented
public  @interface IsValido {

    /*
    Usar BindingResult bindingResult no controller

      */
    String message() default "{constraints.iscontabancariacartorio}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
