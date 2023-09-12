package com.ues.edu.clinica.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ues.edu.clinica.model.Especialidad;
import com.ues.edu.clinica.model.Medico;
import com.ues.edu.clinica.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDTO2 extends RepresentationModel <ConsultaDTO2> {
    private Integer idConsulta;
    private Integer paciente;
    private Integer medico;
    private Integer especialidad;
    private String numConsultorio;
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaConsulta;
    private LocalTime horaConsulta;
}

