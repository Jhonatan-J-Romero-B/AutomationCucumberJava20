package locators;

import org.openqa.selenium.By;

public class Locators {

    public static class NombreDeLaPagina{
        public static final By NOMBRE_DEL_LOCATOR = By.xpath("//etiqueta[@atributo='valorAtributo']");
    }

    public static class VariosDePrueba{
        public static final By CAJA_BUSQUEDA_GOOGLE = By.xpath("//textarea[@name='q' and @id='APjFqb']");
        public static final By PRIMERA_OPCION = By.xpath("(//h3[@class='LC20lb MBeuO DKV0Md'])[1]");
    }
}