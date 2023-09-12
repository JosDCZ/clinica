package com.ues.edu.clinica.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ues.edu.clinica.model.DetalleConsulta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDTOIn {
    private Integer idConsulta;
    private Integer idPaciente;
    private Integer idMedico;
    private Integer idEspecialidad;
    private String numConsultorio;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaConsulta;
    private LocalTime horaConsulta;
    private List<DetalleConsulta> detalleConsulta;
}
