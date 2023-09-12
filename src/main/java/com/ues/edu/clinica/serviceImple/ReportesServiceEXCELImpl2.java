package com.ues.edu.clinica.serviceImple;

import com.ues.edu.clinica.model.Consulta;
import com.ues.edu.clinica.model.Paciente;
import com.ues.edu.clinica.repository.IConsultaRepo;
import com.ues.edu.clinica.repository.IPacienteRepo;
import com.ues.edu.clinica.service.IReporteServiceEXCEL;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReportesServiceEXCELImpl2 implements IReporteServiceEXCEL {
    @Autowired
    private IConsultaRepo consultaRepo;

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pacientes");
        Row filaTitulo = sheet.createRow(0);
        Cell celda = filaTitulo.createCell(1);
        celda.setCellValue("LISTADO GENERAL DE CONSULTAS");
        Row filaData = sheet.createRow(2);
        int dataRowIndice = 1;
        String[] columnas = {"FECHA", "PACIENTE", "MEDICO", "ESPECIALIDAD", "CONSULTORIO"};
        for (int i = 0; i < columnas.length; i++) {
            celda = filaData.createCell(i);
            celda.setCellValue(columnas[i]);
        }

        int dataRowIndex = 4;
        List<Consulta> consultas = consultaRepo.findAll();
        for (Consulta datos : consultas) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(datos.getFechaConsulta());
            dataRow.createCell(2).setCellValue(datos.getMedico().getNombreMedico());
            dataRow.createCell(1).setCellValue(datos.getPaciente().getNombrePaciente());
            dataRow.createCell(3).setCellValue(datos.getEspecialidad().getNombreEspeciadad());
            dataRow.createCell(4).setCellValue(datos.getNumConsultorio());
            dataRowIndex++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }
}
