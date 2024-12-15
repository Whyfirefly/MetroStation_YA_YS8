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

  // проверка отображения времени маршрута
  @Test
  public void checkRouteApproxTimeIsDisplayed() {
    // строим маршрут от «Лубянки» до «Красногвардейской»
    metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
    // проверяем, что у первого маршрута списка отображается нужное примерное время поездки
    Assert.assertEquals("≈ 36 мин.", metroPage.getApproximateRouteTime(0));
  }

  @Test
  public void checkRouteStationFromIsCorrect() {
    // строим маршрут от «Лубянки» до «Красногвардейской»
    metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
    // проверяем, что отображается корректное название станции начала маршрута
    Assert.assertEquals(STATION_LUBYANKA, metroPage.getRouteStationFrom());

  }

  @Test
  public void checkRouteStationToIsCorrect() {
    // строим маршрут от «Лубянки» до «Красногвардейской»
    metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
    // проверяем, что отображается корректное название станции конца маршрута
    Assert.assertEquals(STATION_KRASNOGVARD, metroPage.getRouteStationTo());

  }

  @After
  public void tearDown() {
    driver.quit();
  }
}