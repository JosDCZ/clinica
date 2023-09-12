package com.ues.edu.clinica.controller;

import com.ues.edu.clinica.dtos.PacienteValidDTO;
import com.ues.edu.clinica.exceptions.InvalidDataException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validar")
public class ValidacionesController_BindingResult {
    @PostMapping
    public ResponseEntity<PacienteValidDTO> guardarPaciente(@Valid
                                                            @RequestBody PacienteValidDTO paciente,
                                                            BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.ok(paciente);
    }
}
