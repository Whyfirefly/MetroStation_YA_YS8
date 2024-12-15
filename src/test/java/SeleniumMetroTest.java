import driver.WebDriverVendors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.MetroHomePage;

public class SeleniumMetroTest {
  WebDriverVendors webDriverVendors = new WebDriverVendors();
  // поля для драйвера и страницы
  private WebDriver driver;
  private MetroHomePage metroPage;

  // константы для тестовых данных
  private static final String CITY_SAINTP = "Санкт-Петербург";
  private static final String STATION_SPORTIVNAYA = "Спортивная";
  private static final String STATION_LUBYANKA = "Лубянка";
  private static final String STATION_KRASNOGVARD = "Красногвардейская";

  // все предварительные действия вынеси в Before
  @Before
  public void setUp() {
    driver = WebDriverVendors.get("Chrome");
    // создай объект класса страницы стенда
    metroPage = new MetroHomePage(driver);
    // дождись загрузки страницы
    metroPage.waitForLoadHomePage();
  }

  // проверяем, как работает выбор города
  @Test
  public void checkChooseCityDropdown() {
    // выбираем Санкт-Петербург в списке городов
    metroPage.chooseCity(CITY_SAINTP);
    // проверяем, что станция метро «Спортивная» видна через 8 секунд
    metroPage.waitForStationVisibility(STATION_SPORTIVNAYA);
  }

  @Test
  public void checkRouteApproxTimeIsDisplayed() {
    metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
    Assert.assertEquals("≈ 36 мин.", metroPage.getApproximateRouteTime(0));
  }

  @Test
  public void checkRouteStationFromIsCorrect() {
    metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
    Assert.assertEquals(STATION_LUBYANKA, metroPage.getRouteStationFrom());

  }

  @Test
  public void checkRouteStationToIsCorrect() {
    metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
    Assert.assertEquals(STATION_KRASNOGVARD, metroPage.getRouteStationTo());

  }

  @After
  public void tearDown() {
    driver.quit();
  }
}