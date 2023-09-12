package com.ues.edu.clinica.service;

import com.ues.edu.clinica.model.Especialidad;

import java.util.List;

public interface IEspecialidadService extends ICRUD<Especialidad>{
    List<Especialidad> buscarEspecialidad(String filtro);
}
