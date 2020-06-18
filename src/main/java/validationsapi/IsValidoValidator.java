package validationsapi;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsValidoValidator  implements ConstraintValidator<IsValido, Object> {

    @Override
    public void initialize(IsValido arg0) {
    }

    @Override
    public boolean isValid(Object arg0, ConstraintValidatorContext arg1) {
        if (arg0 == null) {
            arg1.buildConstraintViolationWithTemplate("Objeto está nulo.");
            return false;
        }
        if ("novoStep2" == null) {
            arg1.buildConstraintViolationWithTemplate("Novo coiso 2 é obrigatório").addConstraintViolation();
        } else {
            arg1.buildConstraintViolationWithTemplate("O valor é obrigatório").addPropertyNode("valorAntecipado").addConstraintViolation();
        }

        return true;
    }
}

