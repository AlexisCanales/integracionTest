package com.aim.google;

import java.time.Duration;//para nueva version de selenium 4

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

//para print
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;//para el try

import org.apache.commons.io.FileUtils;//se debe agregar dependecy de commons io

//prueba cambios desde github, para ver si en intellij avisa por lo de fetch remote

//no la sugiere asi que se copia y pega manual
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGoogle {
    private WebDriver driver;

    //para la version junit 5 se usa @BeforeEach y @AfterEach
    @BeforeEach
    public void inicio() {
        // Configuración automática de ChromeDriver con WebDriverManager
        /*
        WebDriverManager.chromedriver().setup();
        System.out.println("✔ WebDriverManager configuró ChromeDriver");
        driver = new ChromeDriver();
        */
        //edge
        // Configuración automática de EdgeDriver con WebDriverManager
        WebDriverManager.edgedriver().setup();
        System.out.println("✔ WebDriverManager configuró EdgeDriver");
        driver = new EdgeDriver();

        driver.manage().window().maximize();
        driver.get("https://www.bing.com/?setlang=es");
    }

    @Test
    public void testGoogle() {
        //google y bing usan el mismo name de la searchbox
        WebElement searchbox = driver.findElement(By.name("q"));
        searchbox.clear();

        searchbox.sendKeys("bootstrap");
        searchbox.submit();
        //nueva version de selenium 4
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //assert clasico
        //assertEquals("bootstrap - Buscar con Google" , driver.getTitle());
        //assert con mensaje personalizado si sale error

        //en chrome, ya que no es el mismo title
        //assertEquals("El título no coincide", "bootstrap - Buscar con Google", driver.getTitle());

        //en edge
        //assertEquals("El título no coincide", "bootstrap - Buscar con Google", driver.getTitle());

        System.out.println("Título actual: " + driver.getTitle());
        System.out.println("⤴️" + driver.getTitle() + "⤵️");
        String esperado = "bootstrap - Búsqueda";
        String actual = driver.getTitle();

        System.out.println("✔ esperado.length: " + esperado.length());
        System.out.println("✔ actual.length:   " + actual.length());

        for (int i = 0; i < Math.min(esperado.length(), actual.length()); i++) {
            if (esperado.charAt(i) != actual.charAt(i)) {
                System.out.println(" Diferencia en posición " + i +
                        ": esperado='" + esperado.charAt(i) +
                        "' (" + (int)esperado.charAt(i) + "), actual='" + actual.charAt(i) +
                        "' (" + (int)actual.charAt(i) + ")");
            }
        }

        //assertEquals("El título no coincide", "bootstrap - Búsqueda", driver.getTitle());

        //screenshot
        try {
            File captura = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //se guarda en C:\Users\TuUsuario\eclipse-workspace\test1nuevo\screenshot.png
            FileUtils.copyFile(captura, new File("./screenshot.png"));
            System.out.println("Captura guardada correctamente");
        } catch (IOException e) {
            System.out.println("Error al guardar la captura: " + e.getMessage());
        }

    }

    @AfterEach
    public void cerrar() {
        //driver.quit();
    }
}

