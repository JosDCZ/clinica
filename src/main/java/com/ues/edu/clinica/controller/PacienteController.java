package com.ues.edu.clinica.controller;

import com.ues.edu.clinica.exceptions.ModeloNotFoundException;
import com.ues.edu.clinica.model.Consulta;
import com.ues.edu.clinica.model.Paciente;
import com.ues.edu.clinica.service.IPacienteService;
import com.ues.edu.clinica.serviceImple.ReportesServiceEXCELImpl;
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
@RequestMapping("/paciente")
public class PacienteController {
    private final IPacienteService servicioPaciente;
    private final ReportesServiceEXCELImpl reportesServiceEXCELImpl;

    @Autowired
    public PacienteController(IPacienteService servicioPaciente, ReportesServiceEXCELImpl reportesServiceEXCELImpl) {
        this.servicioPaciente = servicioPaciente;
        this.reportesServiceEXCELImpl = reportesServiceEXCELImpl;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> mostrarPacientes() {
        List<Paciente> pacientes = this.servicioPaciente.listar();
        return ResponseEntity.ok(pacientes);
    }

    /*@GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Paciente> pacienteById2(@PathVariable("idPaciente") Integer idPaciente) {
        Paciente paciente = this.servicioPaciente.leerPorId(idPaciente);
        return ResponseEntity.ok(paciente);
    }*/

    public PacienteController(ReportesServiceEXCELImpl reportesServiceEXCELImpl, IPacienteService servicioPaciente) {
        this.reportesServiceEXCELImpl = reportesServiceEXCELImpl;
        this.servicioPaciente = servicioPaciente;
    }

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pacientes.xls";
        response.setHeader(headerKey, headerValue);
        reportesServiceEXCELImpl.generateExcel(response);
        response.flushBuffer();
    }

    @GetMapping(value = "/pdf")
    public void pacientesPdf(ModelAndView model, HttpServletResponse response) throws IOException {
        this.servicioPaciente.generarReportePacientes(response);
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> pacienteById(@PathVariable("idPaciente") Integer idPaciente) {
        Paciente paciente = this.servicioPaciente.leerPorId(idPaciente);
        if (paciente.getIdPaciente() == null) {
            throw new ModeloNotFoundException("Paciente no encontrado");
        }
        return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
    }

}
