package com.ues.edu.clinica.serviceImple;

import com.ues.edu.clinica.model.Especialidad;
import com.ues.edu.clinica.model.Medico;
import com.ues.edu.clinica.repository.IEspecialidadRepo;
import com.ues.edu.clinica.service.IEspecialidadService;
import com.ues.edu.clinica.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadServiceImpl implements IEspecialidadService {
    private final IEspecialidadRepo servicioEspecialidad;

    @Autowired
    public EspecialidadServiceImpl(IEspecialidadRepo servicioEspecialidad) {
        // TODO Auto-generated constructor stub
        this.servicioEspecialidad = servicioEspecialidad;
    }

    @Override
    public Especialidad registrar(Especialidad obj) {
        // TODO Auto-generated method stub
        return this.servicioEspecialidad.save(obj);
    }

    @Override
    public Especialidad modificar(Especialidad obj) {
        // TODO Auto-generated method stub
        //return null;
        return this.servicioEspecialidad.save(obj);
    }

    @Override
    public List<Especialidad> listar() {
        List<Especialidad> listEspecialidades= this.servicioEspecialidad.findAll();
        return listEspecialidades;
    }

    @Override
    public Especialidad leerPorId(Integer id) {
        // TODO Auto-generated method stub
        return this.servicioEspecialidad.findById(id).get();
    }

    @Override
    public boolean eliminar(Especialidad obj) {
        // TODO Auto-generated method stub
        try {
            this.servicioEspecialidad.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

    }

    @Override
    public List<Especialidad> buscarEspecialidad(String filtro) {
        // TODO Auto-generated method stub
        return  this.servicioEspecialidad.buscarEspecialidad(filtro);
    }

}
