package methods;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import resources.Logs;
import resources.Resources;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class Methods {
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 0: Variables públicas que pueden ser utilizadas durante toda la vida del test, en cualquier método, y desde cualquier clase que herede de Methods.java. Incluyen sus getters y setters
    Autor: Ing. Jhonatan Romero (QA/QE Automatizador)
    Parámetros de entrada: N/A
    Parámetros de salida: N/A
    Última modificación: 2023-10-14
     */
    @Getter
    @Setter
    public WebDriver webDriver; /**Instancia del navegador**/

    @Getter
    @Setter
    public WebElement webElement; /**Variable pública de tipo WebElement usable durante el ciclo del test**/

    @Getter
    @Setter
    public Wait<WebDriver> waitFluentWait; /**Instancia pública para espera fluída de cierto tiempo definico en el test, y esperar que aparezca un WebElement indicado**/

    @Getter
    @Setter
    public Actions actions; /**Action de Selenium mejora la usabilidad de las acctiones del mouse**/

    /**Todas las localizaciones disponibles en: https://faker.readthedocs.io/en/master/locales.html**/
    @Getter
    @Setter
    public Faker fakerES = new Faker (new Locale("es"));

    @Getter
    @Setter
    public Faker fakerES_AR = new Faker (new Locale("es_AR"));

    @Getter
    @Setter
    public Faker fakerEN = new Faker (new Locale("en"));

    @Getter
    @Setter
    public Faker fakerEN_GB = new Faker (new Locale("en_GB"));
    /**Todas las localizaciones disponibles en: https://faker.readthedocs.io/en/master/locales.html**/

    @Getter
    @Setter
    public JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver; /**Instancia para darle mejor performance a los screenshots y para la optención de los valores de los atributos de un elemento web**/

    @Getter
    @Setter
    public Robot robot = new Robot(); /**Instancia del robot de Java. De igual manera se puede usar la instancia robotKeys**/

    @Getter
    @Setter
    public RobotKeys robotKeys = new RobotKeys(); /**Instancia de la clase RobotKeys. Mejora las acciones del teclado**/

    @Getter
    @Setter
    public String uuid; /**String pública de tipo uuid reutilizable durante el ciclo test**/

    @Getter
    @Setter
    public String publicString; /**String pública reutilizable durante el ciclo del test**/

    @Getter
    @Setter
    public String environment; /**String pública para indicar ambiante de testeo**/

    @Getter
    @Setter
    public List<WebElement> webElementList; /**Lista pública de tipo WebElement usable durante el ciclo del test**/

    @Getter
    @Setter
    public List<String> publicListString = new ArrayList<String>();/**Lista pública donde se pueden almacenar variables de tipo String**/

    @Getter
    @Setter
    public int publicInt; /**Int pública reutilizable durante el ciclo test**/

    @Setter
    public boolean publicBoolean; /**Boolean pública reutilizable durante el ciclo test**/

    public boolean getPublicBoolean() {
        return publicBoolean;
    }

    @Getter
    @Setter
    public List<Integer> publicListInt = new ArrayList<Integer>();/**Lista pública donde se pueden almacenar variables de tipo int**/

    @Getter
    @Setter
    public List[] publicArray; /**Arreglo pública donde se pueden almacenar datos**/

    @Getter
    @Setter
    public String[] publicSplit; /**Arreglo pública para dividir por split**/

    @Getter
    @Setter
    public String testDataFile; /**Variable pública a utilizar para obtener datos del excel de entrada de datos**/

    @Getter
    @Setter
    public String[][] testData_FilaColumna = new String[0][0];/**Matriz pública donde se pueden almacenar datos como en una hoja Excel. El orden es [FILA][COLUMNA]**/

    public Methods() throws AWTException {
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 1: Levanta el Selenium WebDriver con la página indicada en la variable environment
    Autor: Ing. Jhonatan Romero (QA/QE Automatizador)
    Parámetros de entrada: N/A pero se debe setear la UI en la variable pública this.setEnvironment("PageToTest")
    Parámetros de salida: Instancia del WebDriver con la página pedida abierta
    Última modificación: 2023-08-25
     */
    public WebDriver openDriver(){
        try {
            this.setEnvironment(Resources.Environments.GOOGLE);
            log(Logs.INICIO_DEL_TEST);
            log(Logs.ABRIR_NAVEGADOR);
            //System.setProperty(Resources.Drivers.CHROME.get(2), Resources.Drivers.CHROME.get(3));
            System.setProperty(Resources.Drivers.CHROME.get(0), Resources.Drivers.CHROME.get(1));

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(Resources.ChromeOptions.REMOTE_ALLOW_ORIGINS);

            HashMap<String, Object> chromePreferences = new HashMap<String, Object>();
            chromePreferences.put(Resources.ChromePreferences.PROFILE_DEFAULT, 0);
            chromePreferences.put(Resources.ChromePreferences.DOWNLOAD_DEFAULT, Resources.ChromePreferences.DATA_INPUT_EXCEL);
            chromeOptions.setExperimentalOption(Resources.ChromeExperimentalOptions.PREFS, chromePreferences);

            this.setWebDriver(new ChromeDriver(chromeOptions));
            //deleteNow this.webDriver = new ChromeDriver(chromeOptions);
            this.webDriver.manage().window().maximize();
            this.webDriver.get(this.getEnvironment());
            this.setActions(new Actions(this.webDriver));

            JavascriptExecutor executor = (JavascriptExecutor)webDriver;
            executor.executeScript("document.body.style.zoom = '1.0'");
            wait(3);
            log(Logs.NAVEGADOR_ABIERTO);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception openDriver.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError openDriver.\n" + e.getMessage());
        }
        return this.getWebDriver();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 2: Cierra el navegador al finalizar cada test
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: N/A
    Parámetros de salida: N/A
    Última modificación: 2023-08-25
     */
    public void closeDriver(){
        try {
            log(Logs.CERRANDO_NAVEGADOR + this.getEnvironment());
            this.webDriver.close();
            this.webDriver.quit();
            log(Logs.NAVEGADOR_CERRADO);
            log(Logs.FIN_DEL_TEST);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception closeDriver.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError closeDriver.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 3: Fuinción para escribir en consola log cualquier comentario tipo Logs
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: String log es el texto a escribir en la consola de salida
    Parámetros de salida: N/A
    Última modificación: 2023-08-25
     */
    public void log(String log){
        try {
            System.out.println(log);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception log.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError log.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 4: Devuelve la URL en la cual se encuentra el driver al momento de ser llamado el método
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: N/A
    Parámetros de salida: URL de la página actual del browser
    Última modificación: 2023-08-25
     */
    public String getCurrentUrl (){
        try {
            this.setPublicString(this.webDriver.getCurrentUrl());
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getCurrentUrl.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getCurrentUrl.\n" + e.getMessage());
        }
        return this.getPublicString();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 5: Busca un elemento web indicado, en el DOM de la página
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement a buscar
    Parámetros de salida: N/A
    Última modificación: 2023-08-25
     */
    public WebElement findElement (By locator){
        try {
            this.setWebElement(this.webDriver.findElement(locator));
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception findElement.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError findElement.\n" + e.getMessage());
        }
        return this.getWebElement();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 6: Valida si un WebElement se encuentra desplegado en el HTML de la página
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement a buscar si está desplegado en el HTML de la página
    Parámetros de salida: devuelve true o false dependiendo de si se encuentra o no el WebElement desplegado en el HTML de la página
    Última modificación: 2023-08-25
     */
    public boolean isDisplayed (By locator){
        try {
            this.setPublicBoolean(findElement(locator).isDisplayed());
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isDisplayed.\n" +
                    "Elemento \"" + locator.toString() + "\" NO desplegado en la pantalla.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isDisplayed.\n" +
                    "Elemento \"" + locator.toString() + "\" SI desplegado en la pantalla.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 7: Valida si un WebElement NO se encuentra desplegado en el HTML de la página
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement a buscar si NO está desplegado en el HTML de la página
    Parámetros de salida: devuelve true o false dependiendo de si se encuentra o no el WebElement desplegado en el HTML de la página
    Última modificación: 2023-08-25
     */
    public boolean isntDisplayed (By locator){
        try {
            this.setPublicBoolean(findElement(locator).isDisplayed());
            if(this.getPublicBoolean() == Resources.Attributes.FALSE){
                this.setPublicBoolean(Resources.Attributes.TRUE);
            }else{
                this.setPublicBoolean(Resources.Attributes.FALSE);
            }
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isntDisplayed.\n" +
                    "Elemento \"" + locator.toString() + "\" SI desplegado en la pantalla.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isntDisplayed.\n" +
                    "Elemento \"" + locator.toString() + "\" SI desplegado en la pantalla.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 8: Valida si un WebElement se encuentra habilitado en el HTML de la página
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement a buscar si está habilitado en el HTML de la página
    Parámetros de salida: devuelve true o false dependiendo de si está o no habilitado el WebElement en el HTML de la página
    Última modificación: 2023-08-25
     */
    public boolean isEnabled (By locator){
        try{
            this.setPublicBoolean(findElement(locator).isEnabled());
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isEnabled.\n" +
                    "Elemento \"" + locator.toString() + "\" NO habilitado en la pantalla.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isEnabled.\n" +
                    "Elemento \"" + locator.toString() + "\" NO habilitado en la pantalla.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 9: Valida si un WebElement NO se encuentra habilitado en el HTML de la página
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement a buscar si NO está habilitado en el HTML de la página
    Parámetros de salida: devuelve true si NO está habilitado, y false si si está habilitado el WebElement en el HTML de la página
    Última modificación: 2023-08-25
     */
    public boolean isntEnabled (By locator){
        try{
            this.setPublicBoolean(findElement(locator).isEnabled());
            if(this.getPublicBoolean() == Resources.Attributes.FALSE){
                this.setPublicBoolean(Resources.Attributes.TRUE);
            }else{
                this.setPublicBoolean(Resources.Attributes.FALSE);
            }
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isntEnabled.\n" +
                    "Elemento \"" + locator.toString() + "\" SI habilitado en la pantalla.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isntEnabled.\n" +
                    "Elemento \"" + locator.toString() + "\" SI habilitado en la pantalla.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Valida si un WebElement se encuentra presente en la pantalla actual
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement a buscar si está presente en la pantalla actual
    Parámetros de salida: Devuelve "true" o "false" si consigue o no respectivamente el WebElement en la pantalla actual
    Última modificación: 2023-10-27
     */
    public boolean isPresent (By locator){
        try {
            this.setPublicBoolean(!webDriver.findElements(locator).isEmpty());
        }catch (Exception e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isPresent.\n" +
                    "Elemento \"" + locator.toString() + "\" NO presente en la pantalla.\n" + e.getMessage());
        }catch (AssertionError e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isPresent.\n" +
                    "Elemento \"" + locator.toString() + "\" NO presente en la pantalla.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Valida si un WebElement NO se encuentra presente en la pantalla actual
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement a buscar si NO está presente en la pantalla actual
    Parámetros de salida: Devuelve "true" o "false" si consigue o no respectivamente el WebElement en la pantalla actual
    Última modificación: 2023-10-27
     */
    public boolean isntPresent (By locator){
        try {
            this.setPublicBoolean(webDriver.findElements(locator).isEmpty());
        }catch (Exception e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isPresent.\n" +
                    "Elemento \"" + locator.toString() + "\" SI presente en la pantalla.\n" + e.getMessage());
        }catch (AssertionError e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isPresent.\n" +
                    "Elemento \"" + locator.toString() + "\" SI presente en la pantalla.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Valida si un WebElement se encuentra seleccionado en pantalla
    Autor: QE Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement a buscar si está seleccionado en pantalla
    Parámetros de salida: Devuelve "true" si está seleccionado, o "false" si NO está seleccionado, el WebElement en la página
    Última modificación: 2023-10-27
     */
    public boolean isSelected (By locator){
        try {
            this.setPublicBoolean(findElement(locator).isSelected());
        }catch (Exception e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isSelected.\n" + e.getMessage());
        }
        catch (AssertionError e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isSelected.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Valida si un WebElement se encuentra seleccionado en pantalla
    Autor: QE Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement a buscar si está seleccionado en pantalla
    Parámetros de salida: Devuelve "true" si NO está seleccionado, o "false" si SI está seleccionado, el WebElement en la página
    Última modificación: 2023-10-27
     */
    public boolean isntSelected (By locator){
        try {
            this.setPublicBoolean(findElement(locator).isSelected());
            if (this.getPublicBoolean() == Resources.Attributes.FALSE){
                this.setPublicBoolean(Resources.Attributes.TRUE);
            }else{
                this.setPublicBoolean(Resources.Attributes.FALSE);
            }
        }catch (Exception e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception isntSelected.\n" + e.getMessage());
        }
        catch (AssertionError e){
            this.setPublicBoolean(Resources.Attributes.FALSE);
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError isntSelected.\n" + e.getMessage());
        }
        return this.getPublicBoolean();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 10: Obtener el texto del WebElement buscado en la pantalla
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el elemento web del cual se quiere obtener el texto
    Parámetros de salida: devuelve el texto encontrado en la variable publicString
    Última modificación: 2023-10-27
     */
    public String getText (By locator){
        try {
            this.setPublicString(findElement(locator).getText());
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getText.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getText.\n" + e.getMessage());
        }
        return this.getPublicString();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Obtiene el título de la pestaña donde se encuentra navegando
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: N/A
    Parámetros de salida: devuelve un String con el título de la pestaña donde se encuentra navegando
    Última modificación: 2023-10-29
     */
    public String getTitle (){
        String getTitle = "";
        try {
            getTitle = webDriver.getTitle();
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getTitle.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getTitle.\n" + e.getMessage());
        }
        return getTitle;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 11: Devuelve el WebDriver al script que lo llame en su cuerpo de código
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: N/A
    Parámetros de salida: devuelve el WebDriver para usarlo directamente en el test, por ejemplo cuando se necesita para un iframe
    Última modificación: 2023-10-27
     */
    public WebDriver getDriver (){
        try {
            this.setWebDriver(this.webDriver);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getDriver.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getDriver.\n" + e.getMessage());
        }
        return this.getWebDriver();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 12: Redirecciona el navegador a una página URL indicada, en el mismo webDriver abierto
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "URL" es la página a la cual se quiere redireccionar el navegador
    Parámetros de salida: N/A
    Última modificación: 2023-08-25
     */
    public void navigateTo (String URL){
        try{
            this.webDriver.navigate().to(URL);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception navigateTo.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError navigateTo.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 13: Espera mediante Thread.sleep de java; los segundos indicados
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "seconds" son los segundos que se necesita esperar. Espera hasta finalizar el tiempo pedido
    Parámetros de salida: N/A
    Última modificación: 2023-08-25
     */
    public void wait (int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception wait.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError wait.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 14: Espera 30 segundos por un WebElement, y consulta cada 1 segundo que aparezca dicho WebElement
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement a buscarcada 1 segundo, durante 30 segundos
    Parámetros de salida: N/A
    Última modificación: 2023-10-27
     */
    public void wait30(By locator){
        try{
            for (int i = 1; i < 31; i++){
                wait(1);
                if (isPresent(locator)){
                    log("En el segundo " + i + "cargó el localizador " + locator.toString() + " en la pantalla");
                    break;
                }
                if (i == 30){
                    Assert.assertTrue(Resources.Attributes.FALSE, "Pasaron 30 segundos y no cargó el localizador " + locator.toString() + " en la pantalla\n");
                }
            }
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception wait30.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError wait30.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 14: Espera "n"" segundos por un WebElement, y consulta cada 1 segundo que aparezca dicho WebElement
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement a buscarcada 1 segundo, durante 30 segundos
    Parámetros de salida: N/A
    Última modificación: 2023-10-27
     */
    public void waitIsPresent(By locator, int totalTimeSeconds){
        try{
            for (int i = 1; i < (totalTimeSeconds+1); i++){
                wait(1);
                if (isPresent(locator)){
                    log("En el segundo " + i + "cargó el localizador " + locator.toString() + " en la pantalla");
                    break;
                }
                if (i == totalTimeSeconds){
                    Assert.assertTrue(Resources.Attributes.FALSE, "Pasaron " + totalTimeSeconds + " segundos y no cargó el localizador " + locator.toString() + " en la pantalla\n");
                }
            }
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception waitIsPresent.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError waitIsPresent.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 15: Espera por un WebElement consultado cada 1 segundo, durante el tiempo indicado como parámetro de entrada
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement a buscar, totalTimeSeconds es el tiempo máximo a esperar que aparezca el WebElement. La consulta es cada 1 segundo
    Parámetros de salida: N/A
    Última modificación: 2023-08-25
     */
    public void waitFluentWait (By locator, int totalTimeSeconds){
        try {
            this.setWaitFluentWait(new FluentWait<WebDriver>(this.getWebDriver())
                    .withTimeout(Duration.ofSeconds(totalTimeSeconds))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class));
            this.setWebElement(this.getWaitFluentWait().until(new Function<WebDriver, WebElement>(){
                public WebElement apply(WebDriver driver){
                    return driver.findElement(locator);}}));
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception waitFluentWait.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError waitFluentWait.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 16: Hace un clic normal a un WebElement indicado, método de Selenium
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement al cual se le hará clic
    Parámetros de salida: N/A
    Última modificación: 2023-10-29
     */
    public void clic(By locator){
        try {
            findElement(locator).click();
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception clic.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError clic.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 16: Hace un clic normal a un WebElement indicado pero con el objeto de tipo Actions
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: locator es el WebElement al cual se le hará clic
    Parámetros de salida: N/A
    Última modificación: 2023-08-25
     */
    public void clicActions(final By locator){
        try{
            this.actions.moveToElement(findElement(locator)).click();
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception clicActions.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError clicActions.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 17: Consulta cada 1 segundo, durante 30 segundos, por el WebElement a hacerle clic, hasta encontrarlo y hacerle clic
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement al cual se le hará clic una vez encontrado
    Parámetros de salida: N/A
    Última modificación: 2023-10-27
     */
    public void clicWait(final By locator){
        try {
            for(int i = 1; i < 31; i++){
                wait(1);
                if (isPresent(locator)){
                    clic(locator);
                    break;
                }
                if (i == 30){
                    Assert.assertTrue(Resources.Attributes.FALSE, "Pasaron 30 segundos y no cargó el elemento " + locator.toString() + " para hacerle clic.\n");
                }
            }
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception clicWait.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError clicWait.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción 17: Método similar a pulsar la tecla "enter" a través de Selenium
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: N/A
    Parámetros de salida: N/A
    Última modificación: 2023-10-29
     */
    public void submit(By locator){
        try {
            findElement(locator).submit();
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception submit.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError submit.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Borra lo que esté escrito en el input, y luego escribe por pantalla un texto enviado por parámetro
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement del DOM de la página en el cual se va a escribir; "inputText" es el texto que se va a escribir en el WebElement
    Parámetros de salida: N/A
    Última modificación: 2023-10-27
     */
    public void sendKeysSelenium(By locator, String inputText) {
        try{
            clic(locator);
            sendClearSelenium(locator);
            findElement(locator).sendKeys(inputText);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception sendKeysClear.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError sendKeysClear.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Borra el contenido escrito en un WebElement de tipo "input" con el Keys de selenium
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement del DOM de la página, del cual se va a borrar lo que esté escrito
    Parámetros de salida: N/A
    Última modificación: 2023-10-27
     */
    public void sendClearSelenium(By locator) {
        try{
            findElement(locator).sendKeys(Keys.CONTROL + "a", Keys.DELETE, Keys.DELETE);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception sendClearKeys.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError sendClearKeys.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Obtiene un atributo pedido de un WebElement
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement del DOM de la página; al cual le va a buscar el atributo. Y "attribute" es el tipo de atributo que se le va a solicitar al WebElement
    Parámetros de salida: Devuelve el texto del atributo solicitado
    Última modificación: 2023-10-27
     */
    public String getAttribute (By locator, String attribute) {
        String getAttribute = "";
        try{
            getAttribute =  findElement(locator).getAttribute(attribute);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getAttribute.\n" + e.getMessage());
        }
        catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getAttribute.\n" + e.getMessage());
        }
        return getAttribute;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Obtiene el atributo "placeholder" de un WebElement
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement del DOM de la página; al cual le va buscar el atributo "placeholder"
    Parámetros de salida: Devuelve el placeholder solicitado
    Última modificación: 2023-10-27
     */
    public String getPlaceholder (By locator){
        String getPlaceholder = "";
        try{
            getPlaceholder = getAttribute(locator, Resources.Attributes.PLACEHOLDER);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getPlaceholder.\n" + e.getMessage());
        }
        catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getPlaceholder.\n" + e.getMessage());
        }
        return getPlaceholder;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Obtiene el atributo "value" de un WebElement
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement del DOM de la página; al cual le va buscar el atributo "value"
    Parámetros de salida: Devuelve el value solicitado
    Última modificación: 2023-10-27
     */
    public String getValue (By locator){
        String getValue = "";
        try{
            getValue = getAttribute(locator, Resources.Attributes.PLACEHOLDER);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getValue.\n" + e.getMessage());
        }
        catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getValue.\n" + e.getMessage());
        }
        return getValue;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Remueve un atributo de un WebElement, en el DOM de la página
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement del DOM de la página; al cual le va remover el atributo. Y "attribute" es el tipo de atributo que se le va a remover al WebElement
    Parámetros de salida: N/A
    Última modificación: 2023-10-27
     */
    public void removeAttributeById (By locator, String attribute) {
        try{
            this.setPublicString(locator.toString());
            this.publicSplit = this.getPublicString().split(" ");
            javascriptExecutor = (JavascriptExecutor) webDriver;
            javascriptExecutor.executeScript("document.getElementById('" + this.publicSplit[1] + "').removeAttribute('"+ attribute +"')");
            wait(1);
        }
        catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception removeAttributeById.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError removeAttributeById.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Setea un atributo a un WebElement, en el DOM de la página
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el WebElement del DOM de la página; al cual le va a setear el atributo. Y "attribute" es el tipo de atributo que se le va a setear al WebElement
    Parámetros de salida: N/A
    Última modificación: 2023-10-27
     */
    public void setAttributeBtId (By locator, String attribute, String value) {
        try {
            this.setPublicString(locator.toString());
            this.publicSplit = this.getPublicString().split(" ");
            javascriptExecutor = (JavascriptExecutor) webDriver;
            javascriptExecutor.executeScript("document.getElementById('" + this.publicSplit[1] + "').setAttribute('" + attribute + "','" + value + "')");
            wait(1);
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception setAttributeBtId.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError setAttributeBtId.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Posiciona el mouse sobre un WebElement indicado, a través de la librería Actions
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el elemento web sobre el cual se va a posicionar el mouse
    Parámetros de salida: N/A
    Última modificación: 2023-10-29
     */
    public void hover (By locator) {
        try {
            actions.moveToElement(findElement(locator)).build().perform();
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception hover.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError hover.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Posiciona el mouse sobre un WebElement indicado, a través de la librería Actions, y lo resalta de amarillo
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el elemento web sobre el cual se va a posicionar el mouse
    Parámetros de salida: N/A
    Última modificación: 2023-10-29
     */
    public void hoverYellow (By locator) {
        try {
            actions.moveToElement(findElement(locator)).build().perform();
            javascriptExecutor = (JavascriptExecutor) webDriver;
            webElement = webDriver.findElement(locator);
            javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", webElement);
            javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'border: solid 2px white;');", webElement);
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception hoverYellow.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError hoverYellow.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Hace un hover parpadeante sobre un WebElement indicado
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el elemento web sobre el cual se hará el hover parpadeante
    Parámetros de salida: N/A
    Última modificación: 2023-10-29
     */
    public void hoverBlink (By locator) {
        try {
            actions.moveToElement(findElement(locator)).build().perform();
            for (int i = 0; i < 2; i++){
                javascriptExecutor = (JavascriptExecutor) webDriver;
                webElement = webDriver.findElement(locator);
                javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: blue; border: 2px solid red;');", webElement);
                javascriptExecutor = (JavascriptExecutor) webDriver;
                webElement = webDriver.findElement(locator);
                javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", webElement);
            }
            javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'border: solid 2px white;');", webElement);
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception hoverBlink.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError hoverBlink.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Hace un hover parpadeante sobre un WebElement indicado, captura la pantalla, y le hace clic al WebElement
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "locator" es el elemento web sobre el cual se hará el hover parpadeante y clic
    Parámetros de salida: N/A
    Última modificación: 2023-11-17
     */
    public void hoverBlinkClic (By locator) {
        try {
            actions.moveToElement(findElement(locator)).build().perform();
            for (int i = 0; i < 1; i++){
                javascriptExecutor = (JavascriptExecutor) webDriver;
                webElement = webDriver.findElement(locator);
                javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: blue; border: 2px solid red;');", webElement);
                wait(1);
                javascriptExecutor = (JavascriptExecutor) webDriver;
                webElement = webDriver.findElement(locator);
                javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", webElement);
                wait(1);
            }
            javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'border: solid 2px white;');", webElement);
            clic(locator);
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception hoverBlinkClic.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError hoverBlinkClic.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Hace scroll en la pantalla
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "scroll" es el número de veces que va a hacer scroll. Número positivo hace scrollDown. Número negativo hace scrollUp
    Parámetros de salida: N/A
    Última modificación: 2023-11-17
     */
    public void scroll (int scroll) {
        try{
            //String cantidad = "scroll (0," + intToString(scroll * 100) + ")";
            //((JavascriptExecutor) webDriver).executeScript(cantidad);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception scroll.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError scroll.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Hace scroll en la pantalla luego de esperar 2 segundos
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "scroll" es el número de veces que va a hacer scroll. Número positivo hace scrollDown. Número negativo hace scrollUp
    Parámetros de salida: N/A
    Última modificación: 2023-11-17
     */
    public void scrollWait (int scroll) {
        try{
            wait(2);
            //String cantidad = "scroll (0," + intToString(scroll * 100) + ")";
            //((JavascriptExecutor) webDriver).executeScript(cantidad);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception scrollWait.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError scrollWait.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Hace screeshont de la pantalla completa, con scroll incluído
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: N/A
    Parámetros de salida: N/A
    Última modificación: 2023-11-17
     */
    public void screenShotFullPage (String folder, String imageName) {
        try{
            Shutterbug.shootPage(webDriver, Capture.FULL, true).withName(getDate() + "-" + imageName).save("./src/test/resources/evidences/" + folder);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception screenShotFullPage.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError screenShotFullPage.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Abre cuadro CMD y ejecuta Newman a la colección solicitada por parámetro
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: jsonNewmanPath es la ruta donde está la colección, ejemplo (./src/test/resources/newman/collection.postman.json)
    Parámetros de salida: N/A
    Última modificación: 2023-11-17
     */
    public void cmdNewman (String jsonNewmanPath) {
        String newman = null;
        try{
            Process process = Runtime.getRuntime().exec("cmd /c start cmd.exe /K\" cd ./src/test/resources/newman && newman run " + jsonNewmanPath + " -r htmlextra && exit");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((newman = bufferedReader.readLine()) != null){
                log(newman);
            }
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception cmdNewman.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError cmdNewman.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Escribe en el log "n" líneas de separación para que el lector se guíe en el mismo
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "n" es la cantidad de línea de searación que se quieren imprimir. Debe ser igual o mayor a 1
    Parámetros de salida: N/A
    Última modificación: 2023-11-17
     */
    public void separator (int n) {
        try{
            if (n <= 0){
                log("Debes ingresar un número entero positivo mayor a cero");
            } else {
                for (int i = 0; i < n; i++) {
                    log("\n- + - + - + - + - + - + - + - + - + - + - + - + - + - + - + - + - + - + - + - + -\n");
                }
            }
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception separator.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError separator.\n" + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Devuelve la fecha en formato yyyy_MM_dd_HH_mm_ss_SSSS
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: N/A
    Parámetros de salida: String "getDate" contiene la fecha solicitada
    Última modificación: 2023-11-17
     */
    public String getDate () {
        String getDate = "";
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSSS");
            Date date = new Date();
            getDate = dateFormat.format(date);
        }catch (Exception e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception getDate.\n" + e.getMessage());
        }catch (AssertionError e){
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError getDate.\n" + e.getMessage());
        }
        return getDate;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


















    /*
    Descripción: Devuelve data falsa "faker" para pruebas (País: España)
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "parameter" es el tipo de dato que se solicita al método
    Parámetros de salida: devuelve un String con el dato solicitado
    Última modificación: 2023-10-29
     */
    public String fakerDataES (String parameter) {
        String fakerDataES = "";
        try {
            switch (parameter){
                case "name":
                    fakerDataES = fakerES.name().name();
                    break;
                case "firstName":
                    fakerDataES = fakerES.name().firstName();
                    break;
                case "gameOfThronesCharacter":
                    fakerDataES = fakerES.gameOfThrones().character();
                    break;
                default:
                    Assert.assertTrue(Resources.Attributes.FALSE, "Parámetro \"" + parameter + "\" no válido para método fakerDataES");
                    break;
            }
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception fakerDataES.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError fakerDataES.\n" + e.getMessage());
        }
        return fakerDataES;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Devuelve data falsa "faker" para pruebas (País: Argentina)
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "parameter" es el tipo de dato que se solicita al método
    Parámetros de salida: devuelve un String con el dato solicitado
    Última modificación: 2023-10-29
     */
    public String fakerDataES_AR (String parameter) {
        String fakerDataES_AR = "";
        try {
            switch (parameter){
                case "name":
                    fakerDataES_AR = fakerES_AR.name().name();
                    break;
                case "firstName":
                    fakerDataES_AR = fakerES_AR.name().firstName();
                    break;
                case "gameOfThronesCharacter":
                    fakerDataES_AR = fakerES_AR.gameOfThrones().character();
                    break;
                default:
                    Assert.assertTrue(Resources.Attributes.FALSE, "Parámetro \"" + parameter + "\" no válido para método fakerDataES_AR");
                    break;
            }
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception fakerDataES_AR.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError fakerDataES_AR.\n" + e.getMessage());
        }
        return fakerDataES_AR;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Devuelve data falsa "faker" para pruebas (País: Estados Unidos)
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "parameter" es el tipo de dato que se solicita al método
    Parámetros de salida: devuelve un String con el dato solicitado
    Última modificación: 2023-10-29
     */
    public String fakerDataEN (String parameter) {
        String fakerDataEN = "";
        try {
            switch (parameter){
                case "name":
                    fakerDataEN = fakerES_AR.name().name();
                    break;
                case "firstName":
                    fakerDataEN = fakerES_AR.name().firstName();
                    break;
                case "gameOfThronesCharacter":
                    fakerDataEN = fakerES_AR.gameOfThrones().character();
                    break;
                default:
                    Assert.assertTrue(Resources.Attributes.FALSE, "Parámetro \"" + parameter + "\" no válido para método fakerDataEN");
                    break;
            }
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception fakerDataEN.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError fakerDataEN.\n" + e.getMessage());
        }
        return fakerDataEN;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Descripción: Devuelve data falsa "faker" para pruebas (País: Gran Bretaña)
    Autor: Ing. Jhonatan Romero
    Parámetros de entrada: "parameter" es el tipo de dato que se solicita al método
    Parámetros de salida: devuelve un String con el dato solicitado
    Última modificación: 2023-10-29
     */
    public String fakerDataEN_GB (String parameter) {
        String fakerDataEN_GB = "";
        try {
            switch (parameter){
                case "name":
                    fakerDataEN_GB = fakerES_AR.name().name();
                    break;
                case "firstName":
                    fakerDataEN_GB = fakerES_AR.name().firstName();
                    break;
                case "gameOfThronesCharacter":
                    fakerDataEN_GB = fakerES_AR.gameOfThrones().character();
                    break;
                default:
                    Assert.assertTrue(Resources.Attributes.FALSE, "Parámetro \"" + parameter + "\" no válido para método fakerDataEN_GB");
                    break;
            }
        }catch (Exception e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en Exception fakerDataEN_GB.\n" + e.getMessage());
        }catch (AssertionError e) {
            Assert.assertTrue(Resources.Attributes.FALSE, Logs.FAILED_TEST_METHOD + "ERROR en AssertionError fakerDataEN_GB.\n" + e.getMessage());
        }
        return fakerDataEN_GB;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}