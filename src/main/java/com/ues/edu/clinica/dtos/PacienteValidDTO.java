package com.ues.edu.clinica.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteValidDTO {

    @NotBlank(message = "El nombre del paciente no debe ser vacio")
    @NotNull(message = "El nombre del paciente no debe ser nulo")
    @Size(min = 3 , max= 30, message = "El nombre del paciente debe como minimo 3 caracteres y como maximo 30 caracteres")
    private String nombrePaciente;


    @NotBlank(message = "El apellido del paciente no debe ser vacio")
    @NotNull(message = "El apellido del paciente no debe ser nulo")
    private String apellidoPaciente;

    @Size(max = 10, message = "El DUI debe tener como maximo 10 caracteres, incluye el guion")
    private String identPaciente;


    private String direccionPaciente;


    @NotBlank(message = "El numero de telefono no debe ser vacio")
    private String telefonoPaciente;


    @NotEmpty
    @Email
    private String emailPaciente;


    @AssertTrue
    private boolean tieneExpediente;

    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "debe ser una fecha inferior al presente")
    private LocalDate fechaNacimiento;


    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "debe ser una fecha inferior al presente")
    private LocalDate fechaVencimiento;
}
