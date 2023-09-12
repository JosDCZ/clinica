package com.ues.edu.clinica.controller;

import com.ues.edu.clinica.dtos.ConsultaDTO;
import com.ues.edu.clinica.dtos.ConsultaDTO2;
import com.ues.edu.clinica.dtos.ConsultaDTOIn;
import com.ues.edu.clinica.dtos.ConsultaDTOOut;
import com.ues.edu.clinica.model.*;
import com.ues.edu.clinica.service.IConsultaService;
import com.ues.edu.clinica.service.IEspecialidadService;
import com.ues.edu.clinica.service.IMedicoService;
import com.ues.edu.clinica.service.IPacienteService;
import com.ues.edu.clinica.serviceImple.ConsultaServiceImpl;
import com.ues.edu.clinica.serviceImple.ReportesServiceEXCELImpl;
import com.ues.edu.clinica.serviceImple.ReportesServiceEXCELImpl2;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    private final ReportesServiceEXCELImpl2 reportesServiceEXCELImpl2;
    private final IConsultaService consultaService;
    private ConsultaServiceImpl consultaServiceImpl;
    private IMedicoService servicioMedico;
    private IPacienteService servicioPaciente;
    private IEspecialidadService servicioEspecialidad;
    private Medico medico;
    private Especialidad especialidad;
    private Paciente paciente;
    private Consulta consulta;



    public ConsultaController(ReportesServiceEXCELImpl2 reportesServiceEXCELImpl, IConsultaService servicioConsulta) {
        this.reportesServiceEXCELImpl2 = reportesServiceEXCELImpl;
        this.consultaService = servicioConsulta;
    }

    @Autowired
    public ConsultaController(ReportesServiceEXCELImpl2 reportesServiceEXCELImpl2, IConsultaService consultaService, ConsultaServiceImpl consultaServiceImpl, IMedicoService servicioMedico, IPacienteService servicioPaciente, IEspecialidadService servicioEspecialidad) {
        this.reportesServiceEXCELImpl2 = reportesServiceEXCELImpl2;
        this.consultaService = consultaService;
        this.consultaServiceImpl = consultaServiceImpl;
        this.servicioMedico = servicioMedico;
        this.servicioPaciente = servicioPaciente;
        this.servicioEspecialidad = servicioEspecialidad;
    }

    @GetMapping("/hateos2")
    public ResponseEntity<List<ConsultaDTO2>> getAllHateos2() {
        List<Consulta> listConsulta = this.consultaService.listar();
        List<ConsultaDTO2> listConsultaDTO2 = new ArrayList<ConsultaDTO2>();
        if (listConsulta.size() > 0) {
            listConsulta.stream().forEach((c) -> {
                ConsultaDTO2 consultaDTO2 = new ConsultaDTO2(
                        c.getIdConsulta(),
                        c.getPaciente().getIdPaciente(),
                        c.getMedico().getIdMedico(),
                        c.getEspecialidad().getIdEspecialidad(),
                        c.getNumConsultorio(),
                        c.getFechaConsulta(),
                        c.getHoraConsulta()
                );
                //Link de medico
                Link medicoLink = linkTo(methodOn(MedicoController.class).medicoById(c.getMedico().getIdMedico())).withSelfRel();
                //Link de paciente
                Link pacienteLink = linkTo(methodOn(PacienteController.class).pacienteById(c.getPaciente().getIdPaciente())).withSelfRel();
                //Link de especialidad
                Link especialidadLink = linkTo(methodOn(EspecialidadController.class).especialidadById(c.getEspecialidad().getIdEspecialidad())).withSelfRel();
                consultaDTO2.add(medicoLink);
                consultaDTO2.add(pacienteLink);
                consultaDTO2.add(especialidadLink);
                listConsultaDTO2.add(consultaDTO2);

            });

        }
        return new ResponseEntity<List<ConsultaDTO2>>(listConsultaDTO2, HttpStatus.OK);
    }


    @GetMapping("/hateos")
    public ResponseEntity<List<ConsultaDTO>> getAllHateos() {
        List<Consulta> listConsulta = this.consultaService.listar();
        List<ConsultaDTO> listConsultaDTO = new ArrayList<ConsultaDTO>();
        if (listConsulta.size() > 0) {
            listConsulta.stream().forEach((c) -> {
                ConsultaDTO consultaDTO = new ConsultaDTO(
                        c.getIdConsulta(),
                        c.getPaciente(),
                        c.getMedico(), // Modificado: Pasar el objeto Medico completo en lugar de su ID
                        c.getEspecialidad(),
                        c.getNumConsultorio(),
                        c.getFechaConsulta(),
                        c.getHoraConsulta()
                );
                // Resto del código...

                listConsultaDTO.add(consultaDTO);
            });
        }
        return new ResponseEntity<List<ConsultaDTO>>(listConsultaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO2> consultaById(@PathVariable("id") Integer id) {
        Consulta consulta = this.consultaService.leerPorId(id);
        ConsultaDTO2 consultaDTO2 = null;
        if (consulta != null) {
            consultaDTO2 = new ConsultaDTO2(
                    consulta.getIdConsulta(),
                    consulta.getPaciente().getIdPaciente(),
                    consulta.getMedico().getIdMedico(),
                    consulta.getEspecialidad().getIdEspecialidad(),
                    consulta.getNumConsultorio(),
                    consulta.getFechaConsulta(),
                    consulta.getHoraConsulta()
            );
        }
        return new ResponseEntity<ConsultaDTO2>(consultaDTO2, HttpStatus.OK);
    }

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pacientes.xls";
        response.setHeader(headerKey, headerValue);
        reportesServiceEXCELImpl2.generateExcel(response);
        response.flushBuffer();
    }

    @GetMapping(value = "/pdf")
    public void listConsultaMedicasPorEspecialidadPdf(ModelAndView model, HttpServletResponse response) throws IOException {
        this.consultaService.generarReportePorConsulta(response);
    }

    @GetMapping(value = "/report/pdf")
    public void cantidadConsultaMedicasPorEspecialidadPdf(ModelAndView model, HttpServletResponse response) throws IOException {
        this.consultaService.generarReportePorConsultaGraficoBarras(response);
    }

    @GetMapping(value = "/report/pdfpas")
    public void cantidadMedicosPorEspecialidadPdf(ModelAndView model, HttpServletResponse response) throws IOException {
        this.consultaService.generarReportePorCantidadMedicosPorEspGraficoPastel(response);
    }

    @GetMapping(value = "/report/pdfline")
    public void cantidadPacientesPorEspecialidadPdf(ModelAndView model, HttpServletResponse response) throws IOException {
        this.consultaService.generarReportePorCantidadPacientesPorEspGraficoLine(response);
    }

    @GetMapping(value = "/pdfp")
    public void listConsultamedicasPorEspecialidadesPdf(ModelAndView model, HttpServletResponse response, @RequestParam int idEspecialidadParam, @RequestParam String fechaConsultaParam) throws IOException {
        this.consultaService.generarReportePorConsultaParam(response, idEspecialidadParam, fechaConsultaParam);
    }

    @PostMapping("/insertar")
    public ResponseEntity<GenericResponse<ConsultaDTOIn>> guardarConsulta(@RequestBody ConsultaDTOIn consultaDTOIn) {
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponse<ConsultaDTOIn> resp = new GenericResponse<ConsultaDTOIn>(0,
                "ERROR DE ALMACENAMIENTO DE LA CONSULTA", consultaDTOIn);
        Optional<ConsultaDTOIn> opt = Optional.of(consultaDTOIn);
        if (opt.isPresent()) {
            this.medico = new Medico();
            this.especialidad = new Especialidad();
            this.paciente = new Paciente();
            this.consulta = new Consulta();
            this.consulta.setMedico(this.servicioMedico.leerPorId((consultaDTOIn.getIdMedico())));
            this.consulta.setEspecialidad(this.servicioEspecialidad.leerPorId((consultaDTOIn.getIdEspecialidad())));
            this.consulta.setPaciente(this.servicioPaciente.leerPorId((consultaDTOIn.getIdPaciente())));
            this.consulta.setFechaConsulta(consultaDTOIn.getFechaConsulta());
            this.consulta.setHoraConsulta(consultaDTOIn.getHoraConsulta());
            this.consulta.setNumConsultorio(consultaDTOIn.getNumConsultorio());
            if (consultaDTOIn.getDetalleConsulta().size() > 0) {
                consultaDTOIn.getDetalleConsulta().stream().peek(d -> d.setConsulta(consulta))
                        .collect(Collectors.toList());
                try {
                    this.consultaService.registrar(consulta);
                    resp.setCode(1);
                    resp.setMessage("Exito - Consulta Almacenada Exitosamente !!");
                    http = HttpStatus.OK;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return new ResponseEntity<GenericResponse<ConsultaDTOIn>>(resp, http);
    }

    @PutMapping("/modificar")
    public ResponseEntity<GenericResponse<ConsultaDTOIn>> editarConsulta(@RequestBody ConsultaDTOIn consultaDTOIn) {
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponse<ConsultaDTOIn> resp = new GenericResponse<ConsultaDTOIn>(0,
                "ERROR DE ALMACENAMIENTO DE LA CONSULTA", consultaDTOIn);
        Optional<ConsultaDTOIn> opt = Optional.of(consultaDTOIn);
        if (opt.isPresent()) {
            this.medico = new Medico();
            this.especialidad = new Especialidad();
            this.paciente = new Paciente();
            this.consulta = new Consulta();
            this.consulta.setMedico(this.servicioMedico.leerPorId((consultaDTOIn.getIdMedico())));
            this.consulta.setEspecialidad(this.servicioEspecialidad.leerPorId((consultaDTOIn.getIdEspecialidad())));
            this.consulta.setPaciente(this.servicioPaciente.leerPorId((consultaDTOIn.getIdPaciente())));
            this.consulta.setFechaConsulta(consultaDTOIn.getFechaConsulta());
            this.consulta.setHoraConsulta(consultaDTOIn.getHoraConsulta());
            this.consulta.setNumConsultorio(consultaDTOIn.getNumConsultorio());
            if (consultaDTOIn.getDetalleConsulta().size() > 0) {
                consultaDTOIn.getDetalleConsulta().stream().peek(d -> d.setConsulta(consulta))
                        .collect(Collectors.toList());
                try {
                    this.consultaService.modificar(consulta);
                    resp.setCode(1);
                    resp.setMessage("Exito - Consulta Almacenada Exitosamente !!");
                    http = HttpStatus.OK;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return new ResponseEntity<GenericResponse<ConsultaDTOIn>>(resp, http);
    }

    @GetMapping("/dtoout")
    public ResponseEntity<List<ConsultaDTOOut>> getAllConsulta() {
        List<Consulta> consultas = this.consultaService.listar();
        List<ConsultaDTOOut> consultasDTOOut = new ArrayList<>();
        if (consultas.size() > 0) {
            consultas.stream().forEach((c) -> {
                ConsultaDTOOut consultaDTOout = new ConsultaDTOOut(c.getIdConsulta(),
                        c.getPaciente().getNombrePaciente() + " " + c.getPaciente().getApellidoPaciente(),
                        c.getMedico().getNombreMedico() + " " + c.getMedico().getApellidoMedico(),
                        c.getEspecialidad().getNombreEspeciadad(),
                        c.getNumConsultorio(),
                        c.getFechaConsulta(),
                        c.getHoraConsulta());
                // Resto del código...

                consultasDTOOut.add(consultaDTOout);
            });
        }
        return new ResponseEntity<List<ConsultaDTOOut>>(consultasDTOOut, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> mostrartConsultas(){
        List<Consulta> consultas = this.consultaService.listar();
        return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<GenericResponse<Consulta>> guardarConsulta(@RequestBody Consulta consulta) {
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponse<Consulta> resp = new GenericResponse<Consulta>(0,
                "ERROR DE ALMACENAMIENTO DE LA CONSULTA", consulta);
        Optional<Consulta> opt = Optional.ofNullable(consulta);
        Consulta conSelect =  new Consulta();
        if (opt.isPresent()) {
            if (consulta.getDetalleConsulta().size() > 0) {
                consulta.getDetalleConsulta().stream().peek(d -> d.setConsulta(consulta))
                        .collect(Collectors.toList());
                try {
                    conSelect = this.consultaService.registrar(consulta);
                    resp.setCode(1);
                    resp.setResponse(conSelect);
                    resp.setMessage("Exito - Consulta Almacenada Exitosamente !!");
                    http = HttpStatus.OK;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return new ResponseEntity<GenericResponse<Consulta>>(resp, http);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Consulta>> eliminarConsulta(@PathVariable("id") Integer id){
        Optional<Consulta> opt = Optional.ofNullable(this.consultaService.leerPorId(id));
        GenericResponse<Consulta> resp = new GenericResponse<>();
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        if (opt.isPresent()) {
            if (this.consultaService.eliminar(opt.get())) {
                resp.setCode(1);
                resp.setMessage("Exito - Se elimino la consulta !!");
                resp.setResponse(opt.get());
                } else{
                resp.setCode(0);
                resp.setMessage("Fallo - No se elimino la consulta");
                resp.setResponse(opt.get());
            }
        }else{
            resp.setCode(0);
            resp.setMessage("Fallo - No hay consulta que eliminar");
        }
        return new ResponseEntity<GenericResponse<Consulta>>(resp, http);
    }

}
