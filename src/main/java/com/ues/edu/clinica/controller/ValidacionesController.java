package com.ues.edu.clinica.controller;

import com.ues.edu.clinica.dtos.PacienteValidDTO;
import com.ues.edu.clinica.model.*;
import com.ues.edu.clinica.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/validar")
public class ValidacionesController {

    private final IPacienteService pacienteService;

    public ValidacionesController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/insertar")
    public ResponseEntity<GenericResponse<PacienteValidDTO>> guardarpaciente(@Valid @RequestBody PacienteValidDTO pacienteValidDTO) {
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponse<PacienteValidDTO> resp = new GenericResponse<>(0,
                "ERROR DE ALMACENAMIENTO DE DATOS DEL PACIENTE", pacienteValidDTO);
        Optional<PacienteValidDTO> opt = Optional.of(pacienteValidDTO);
        if (opt.isPresent()) {
            Paciente paciente = new Paciente();
            paciente.setNombrePaciente(pacienteValidDTO.getNombrePaciente());
            paciente.setApellidoPaciente(pacienteValidDTO.getApellidoPaciente());
            paciente.setIdentPaciente(pacienteValidDTO.getIdentPaciente());
            paciente.setEmailPaciente(pacienteValidDTO.getEmailPaciente());
            paciente.setDireccionPaciente(pacienteValidDTO.getDireccionPaciente());
            paciente.setTelefonoPaciente(pacienteValidDTO.getTelefonoPaciente());
            paciente.setFechaVencimiento(pacienteValidDTO.getFechaVencimiento());
            paciente.setFechaNacimiento(pacienteValidDTO.getFechaNacimiento());
            paciente.setTieneExpediente(pacienteValidDTO.isTieneExpediente());
            try {
                this.pacienteService.registrar(paciente);
                resp.setCode(1);
                resp.setMessage("Exito - paciente Almacenado Exitosamente !!");
                http = HttpStatus.OK;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new ResponseEntity<GenericResponse<PacienteValidDTO>>(resp, http);
    }
}
