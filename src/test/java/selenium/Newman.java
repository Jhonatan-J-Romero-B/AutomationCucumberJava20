package selenium;

import methods.Methods;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Newman extends Methods {

    public Newman() throws AWTException {

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @BeforeMethod
    public void BeforeTest(){
        log("Abriendo la prueba de la clase Newman");
    }

    @AfterMethod
    public void AfterTest(){
        log("Cerrando la prueba de la clase Newman");
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void Newman01(){
        try{
            log("Comenzando Newman");
            //Process process = Runtime.getRuntime().exec("cmd /c start cmd.exe /K\" cd ./newman && newman run ReqRes.io.postman_collection.json -r htmlextra && exit");
            Process process = Runtime.getRuntime().exec("cmd /c start cmd.exe /K\" cd ./newman && newman run ReqRes.io.postman_collection.json -r htmlextra && exit");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            /*String newman = null;
            while ((newman = bufferedReader.readLine()) != null){
                log(newman);
            }*/
            log("Terminando Newman");
        }catch (Exception e){
            //LogException("Exception Newman01", e.getMessage());
        }catch (AssertionError e){
            //LogException("AssertionError Newman01", e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void GenerateExcel(){
        try{
            log("Creando Libro Excel");
            //Crear libro de trabajo en blanco
            Workbook workbook = new HSSFWorkbook();

            log("Creando Hoja Nueva con nombre 'Hoja de datos'");
            //Crea hoja nueva
            Sheet sheet = workbook.createSheet("Hoja de datos");

            log("Agregando datos a la 'Hoja de datos'");
            //Por cada línea se crea un arreglo de objetos (Object[])
            Map<String, Object[]> datos = new TreeMap<String, Object[]>();
            datos.put("1", new Object[]{"Identificador", "Nombre", "Apellidos"});
            datos.put("2", new Object[]{1, "María", "Remen"});
            datos.put("3", new Object[]{2, "David", "Allos"});
            datos.put("4", new Object[]{3, "Carlos", "Caritas"});
            datos.put("5", new Object[]{4, "Luisa", "Vitz"});
            //Iterar sobre datos para escribir en la hoja
            Set<String> keyset = datos.keySet();
            int numeroRenglon = 0;
            for (String key : keyset) {
                Row row = sheet.createRow(numeroRenglon++);
                Object[] arregloObjetos = datos.get(key);
                int numeroCelda = 0;
                for (Object obj : arregloObjetos) {
                    Cell cell = row.createCell(numeroCelda++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }
                }
            }

            log("Generando documento Excel");
            //Se genera el documento
            FileOutputStream out = new FileOutputStream(new File("./src/test/resources/testData/excelGenerado.xls"));
            workbook.write(out);
            out.close();

        }catch (Exception e){
            log("Failed by Exception: GenerateExcel\n" + e.getMessage());
        }catch (AssertionError e){
            log("Failed by AssertionError: GenerateExcel\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}