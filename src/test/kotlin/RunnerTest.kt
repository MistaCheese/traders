package com.main

import CVD.CVD
import CVD.check.Driver
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class RunnerTest {
    private lateinit var driver: ChromeDriver

    private val webLink: String = "https://trading-system.tecman.ru/"

    @Before
    fun singUp() {
        CVD("C:\\webDriver\\chrome\\", "C:\\Program Files (x86)\\Google\\Chrome\\Application").check()
        System.setProperty(
            "webdriver.chrome.driver",
            "C:\\webDriver\\chrome\\" + Driver().getLocalDriverSet("C:\\webDriver\\chrome\\")
                .last() + "\\chromedriver.exe"
        )
        driver = ChromeDriver()
        driver.manage()?.timeouts()?.implicitlyWait(10, TimeUnit.SECONDS)
        driver.manage().window().size = Dimension(1920, 1080)
    }

    @Test
    fun check() {
        driver.get(webLink)
        var exm: WebElement =
            driver.findElement(By.xpath("//body/div[@id='app']/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/input[1]")) // Поле с логином
        exm.sendKeys("admin")
        exm =
            driver.findElement(By.xpath("//body/div[@id='app']/div[1]/form[1]/div[2]/div[2]/div[1]/div[1]/input[1]")) // Поле с паролем
        exm.sendKeys("Lh4iX9NkwLeuWw%u")
        exm = driver.findElement(By.xpath("//span[contains(text(),'Войти')]")) // Кнопка войти
        exm.click()
        exm =
            driver.findElement(By.xpath("//body/div[@id='app']/div[1]/div[1]/div[2]/div[2]/section[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[3]")) // Значение в таблице

        val fr = (exm.getAttribute("textContent")
            .toString())
            .filter { !it.isWhitespace() }
            .trim()
            .replace(",", ".")
        val or = String.format("%.4f", getAll().get()).replace(",", ".") // Формат с 4мя знаками после запятой
        val file =
            File("status.txt") // Файл со статусом повторной отправки 0 - пред тест пройден, 1 - предыдущий тест провален
        val users = File("users_id.txt") // Список ID пользователей с телеги
        val logStat = File(LocalDate.now().toString() + " logStat.txt") // Файл с логами
        if (or.toDouble() != fr.toDouble()) {
            logStat.appendText(
                (LocalDateTime.now()
                    .toString() + " Значения не совпадают: $fr - c сайта, $or - с byBit ===========================\n")
            )

//            if (file.readText() == "0") {
            for (i in users.readText().split(",")) { // Отправка сообщений боту по ID пользователя
                getAll().sendMessage("Значения не совпадают: \n$fr - c сайта, \n$or - с byBit :x:", i)
            }
//                file.writeText("1")
//            }

            assertEquals("Значения не совпадают: $fr - c сайта, $or - с byBit", or, fr)

        } else {
            file.writeText("0")
            println("Значения совпадают: $fr - c сайта, $or - с byBit, все ОК")
            logStat.appendText(
                (LocalDateTime.now().toString() + " Значения совпадают: $fr - c сайта, $or - с byBit, все ОК\n")
            )

        }
    }


    @After
    fun shutDown() {
        Thread.sleep(5000)
        driver.quit()

    }

}