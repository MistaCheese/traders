import CVD.CVD
import CVD.check.Driver
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Action
import org.openqa.selenium.interactions.Actions
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class RunnerTest {
    private lateinit var driver: ChromeDriver

    private val webLink: String = "https://trading-system.tecman.ru/"
    private val binanceLink: String = "https://www.binance.com/ru/trade/BTC_USDT?layout=basic"

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
        val builder: Actions = Actions(driver)


        driver.get(webLink)

        var exm: WebElement =
            driver.findElement(By.xpath("//body/div[@id='app']/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/input[1]"))
        exm.sendKeys("admin")
        exm = driver.findElement(By.xpath("//body/div[@id='app']/div[1]/form[1]/div[2]/div[2]/div[1]/div[1]/input[1]"))
        exm.sendKeys("Lh4iX9NkwLeuWw%u")
        exm = driver.findElement(By.xpath("//span[contains(text(),'Войти')]"))
        exm.click()
        exm =
            driver.findElement(By.xpath("//body/div[@id='app']/div[1]/div[1]/div[2]/div[2]/section[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[3]"))

        val fr = (exm.getAttribute("textContent").toString()).replace(".", ",")


        val or = String.format("%.4f", getAll().get())
        assertEquals("Значения не совпадают: $fr - c сайта, $or - с бинанса ", or ,fr)


    }


    @After
    fun shutDown() {
        Thread.sleep(5000)
        driver.quit()

    }

}