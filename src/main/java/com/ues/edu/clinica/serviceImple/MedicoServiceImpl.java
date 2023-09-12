package com.ues.edu.clinica.serviceImple;

import com.ues.edu.clinica.model.Medico;
import com.ues.edu.clinica.repository.IMedicoRepo;
import com.ues.edu.clinica.service.IMedicoService;
import com.ues.edu.clinica.service.IReporteServicePDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class MedicoServiceImpl implements IMedicoService {
    private final IMedicoRepo servicioMedico;
    private final IReporteServicePDF servicioReporte;

    @Autowired
    public MedicoServiceImpl(IMedicoRepo servicioMedico, IReporteServicePDF servicioReporte) {
        this.servicioMedico = servicioMedico;
        this.servicioReporte = servicioReporte;
    }

    @Override
    public Medico registrar(Medico obj) {
        // TODO Auto-generated method stub
        return this.servicioMedico.save(obj);
    }

    @Override
    public Medico modificar(Medico obj) {
        // TODO Auto-generated method stub
        //return null;
        return this.servicioMedico.save(obj);
    }

    @Override
    public List<Medico> listar() {
        List<Medico> listMedicos= this.servicioMedico.findAll();
        return listMedicos;
    }

    @Override
    public Medico leerPorId(Integer id) {
        // TODO Auto-generated method stub
        return this.servicioMedico.findById(id).get();
    }

    @Override
    public boolean eliminar(Medico obj) {
        // TODO Auto-generated method stub
        try {
            this.servicioMedico.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception

            return false;
        }

    }
    @Override
    public List<Medico> buscarMedico(String filtro) {
        // TODO Auto-generated method stub
        return  this.servicioMedico.buscarMedico(filtro) ;
    }

    @Override
    public void generarReporteMedicos(HttpServletResponse response) throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/reports/medicoRep.jrxml");
        this.servicioReporte.generarReporte(stream,response,listar());
    }
}
