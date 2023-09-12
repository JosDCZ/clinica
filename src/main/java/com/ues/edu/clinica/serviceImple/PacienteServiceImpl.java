package com.ues.edu.clinica.serviceImple;

import com.ues.edu.clinica.model.Paciente;
import com.ues.edu.clinica.repository.IPacienteRepo;
import com.ues.edu.clinica.service.IPacienteService;
import com.ues.edu.clinica.service.IReporteServicePDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {
    private final IPacienteRepo servicioPaciente;
    private final IReporteServicePDF servicioReporte;

    @Autowired
    public PacienteServiceImpl(IPacienteRepo servicioPaciente, IReporteServicePDF servicioReporte) {
        this.servicioPaciente = servicioPaciente;
        this.servicioReporte = servicioReporte;
    }

    @Override
    public Paciente registrar(Paciente obj) {
        return this.servicioPaciente.save(obj);
    }

    @Override
    public Paciente modificar(Paciente obj) {
        return this.servicioPaciente.save(obj);
    }

    @Override
    public List<Paciente> listar() {
        java.util.List<Paciente> listPacientes = this.servicioPaciente.findAll();
        return listPacientes;
    }

    @Override
    public Paciente leerPorId(Integer id) {
        return this.servicioPaciente.findById(id).orElse(new Paciente());
    }

    @Override
    public boolean eliminar(Paciente obj) {
        try {
            this.servicioPaciente.delete(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Paciente> buscarPaciente(String filtro) {
        return this.servicioPaciente.buscarPaciente(filtro);
    }

    @Override
    public void generarReportePacientes(HttpServletResponse response) throws IOException{
        final InputStream stream = this.getClass().getResourceAsStream("/reports/pacienteRep.jrxml");
        this.servicioReporte.generarReporte(stream,response,listar());
    }
}
