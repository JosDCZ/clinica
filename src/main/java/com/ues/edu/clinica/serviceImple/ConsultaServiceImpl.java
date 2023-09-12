package com.ues.edu.clinica.serviceImple;

import com.ues.edu.clinica.model.Consulta;
import com.ues.edu.clinica.repository.IConsultaRepo;
import com.ues.edu.clinica.service.IConsultaService;
import com.ues.edu.clinica.service.IReporteServicePDF;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor //ESTO SUSTITUYE A @Autowired
@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final IConsultaRepo consultaRepo;
    private final IReporteServicePDF servicioReportes;

    @Override
    public Consulta registrar(Consulta obj) {
        return this.consultaRepo.save(obj);
    }

    @Override
    public Consulta modificar(Consulta obj) {
        return null;
    }

    @Override
    public List<Consulta> listar() {
        List<Consulta> listConsultas = this.consultaRepo.findAll();
        return listConsultas;
    }

    @Override
    public Consulta leerPorId(Integer id) {
        return this.consultaRepo.findById(id).get();
    }

    @Override
    public boolean eliminar(Consulta obj) {
        try {
            this.consultaRepo.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public void generarReportePorConsulta(HttpServletResponse response) throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/reports/ConsultaClinicaCE.jrxml");
        this.servicioReportes.generarReporte(stream, response, consultaRepo.totalConsultasPacientes());
    }

    @Override
    public void generarReportePorConsultaGraficoBarras(HttpServletResponse response) throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/reports/CantidadConsultasAtendidasPorEspecialidad.jrxml");
        this.servicioReportes.generarReporte(stream, response, consultaRepo.cantidadConsultaPorEspecialidadGrafBarras());
    }

    @Override
    public void generarReportePorCantidadMedicosPorEspGraficoPastel(HttpServletResponse response) throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/reports/medicoEspecialidadRep.jrxml");
        this.servicioReportes.generarReporte(stream, response, consultaRepo.cantidadMedicosPorEspecialidadGrafPastel());
    }

    @Override
    public void generarReportePorCantidadPacientesPorEspGraficoLine(HttpServletResponse response) throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/reports/pacienteEspecialidadRep.jrxml");
        this.servicioReportes.generarReporte(stream, response, consultaRepo.cantidadPacientesPorEspecialidadGrafLine());
    }

    @Override
    public void generarReportePorConsultaParam(HttpServletResponse response, int idEspecialidadParam, String fechaConsultaParam) throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/reports/consultaMedicaEspecifica.jrxml");
        this.servicioReportes.generarReporte(stream, response, consultaRepo.cantidadConsultasPacientes(idEspecialidadParam,fechaConsultaParam));
    }
}
