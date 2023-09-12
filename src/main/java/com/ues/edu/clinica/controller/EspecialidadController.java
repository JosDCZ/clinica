package com.ues.edu.clinica.controller;

import com.ues.edu.clinica.model.Especialidad;
import com.ues.edu.clinica.model.Medico;
import com.ues.edu.clinica.service.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {
    private final IEspecialidadService servicioEspecialidad;

    @Autowired
    public EspecialidadController(IEspecialidadService servicioEspecialidad) { this.servicioEspecialidad = servicioEspecialidad; }

    @GetMapping
    public ResponseEntity<List<Especialidad>> mostrarEspecialidad(){
        List<Especialidad> especialidad = this.servicioEspecialidad.listar();
        return ResponseEntity.ok(especialidad);
    }

    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<Especialidad> especialidadById(@PathVariable("idEspecialidad") Integer idEspecialidad){
        Especialidad especialidad= this.servicioEspecialidad.leerPorId(idEspecialidad);
        return new ResponseEntity<Especialidad>(especialidad, HttpStatus.OK);
    }
}
