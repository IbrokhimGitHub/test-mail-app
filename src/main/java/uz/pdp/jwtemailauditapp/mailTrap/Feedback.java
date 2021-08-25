package uz.pdp.jwtemailauditapp.mailTrap;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@Data
public class Feedback {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String feedback;

}
