package selenium;

import locators.Locators;
import locators.Textos;
import methods.Methods;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resources.Logs;
import resources.Resources;
import java.awt.*;

public class Tests01 extends Methods {

    public Tests01() throws AWTException{

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @BeforeMethod
    public void BeforeTest(){
        openDriver();
    }

    @AfterMethod
    public void AfterTest(){
        closeDriver();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void Test01(){
        try{
            System.out.println("Este es el comienzo del Test01");
            Assert.assertTrue(Resources.Attributes.TRUE);
            System.out.println("Este es el fin del Test01");
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_SCRIPT + "ERROR en Exception Test01.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_SCRIPT + "ERROR en AssertionError Test01.\n" + e.getMessage());
        }

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void Test02(){
        System.out.println("Este es el comienzo del Test02");

        System.out.println("Se le manda \"sendClear\" a la caja de búsqueda de Google");
        sendClearSelenium(Locators.VariosDePrueba.CAJA_BUSQUEDA_GOOGLE);

        System.out.println("Se espera 1 segundo");
        wait(1);

        System.out.println("Se manda a buscar \"Videos de gatos\" en la caja de búsqueda de Google");
        sendKeysSelenium(Locators.VariosDePrueba.CAJA_BUSQUEDA_GOOGLE,"Videos de gatos");

        System.out.println("Se pulsa la tecla Enter");
        submit(Locators.VariosDePrueba.CAJA_BUSQUEDA_GOOGLE);

        System.out.println("Se esperan 3 segundos hasta que cargue la página solicitada");
        wait(3);

        System.out.println("Se valida el texto del primer resultado encontrado: " + Textos.VariosDePrueba.PRIMERA_OPCION);
        Assert.assertEquals(getText(Locators.VariosDePrueba.PRIMERA_OPCION), Textos.VariosDePrueba.PRIMERA_OPCION, Logs.FAILED_TEST_SCRIPT + "Resultado de la página: " + getText(Locators.VariosDePrueba.PRIMERA_OPCION) + "\n Resultado esperado: " + Textos.VariosDePrueba.PRIMERA_OPCION);

        System.out.println("Se hace clic en la primera opción");
        wait30(Locators.VariosDePrueba.PRIMERA_OPCION);
        clic(Locators.VariosDePrueba.PRIMERA_OPCION);

        System.out.println("Se valida el título de la pestaña actual debe ser: " + Textos.VariosDePrueba.TITULO_DE_PAGINA);
        Assert.assertEquals(getTitle(), Textos.VariosDePrueba.TITULO_DE_PAGINA, Logs.FAILED_TEST_SCRIPT + "Resultado de la página: " + getCurrentUrl() + "\n Resultado esperado: " + Textos.VariosDePrueba.TITULO_DE_PAGINA);
        System.out.println("La validación fue exitosa");
        System.out.println("Este es el fin del Test02");
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}