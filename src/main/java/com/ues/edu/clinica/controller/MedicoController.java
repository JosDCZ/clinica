package com.ues.edu.clinica.controller;

import com.ues.edu.clinica.model.Medico;
import com.ues.edu.clinica.model.Paciente;
import com.ues.edu.clinica.service.IMedicoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {
    private final IMedicoService servicioMedico;

    @Autowired
    public MedicoController(IMedicoService servicioMedico) {
        this.servicioMedico = servicioMedico;
    }


    @GetMapping
    public ResponseEntity<List<Medico>> mostrarMedicos(){
        List<Medico> medicos = this.servicioMedico.listar();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<Medico> medicoById(@PathVariable("idMedico") Integer idMedico){
        Medico medico= this.servicioMedico.leerPorId(idMedico);
        return new ResponseEntity<Medico>(medico,HttpStatus.OK);
    }

    @GetMapping(value = "/pdf")
    public void medicosPdf(ModelAndView model, HttpServletResponse response) throws IOException {
        this.servicioMedico.generarReporteMedicos(response);
    }
}
