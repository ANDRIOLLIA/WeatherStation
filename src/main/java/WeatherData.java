import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.util.ArrayList;

public class WeatherData implements Subject {
    private String htmlCode = "";
    private ArrayList<Observer> observers;
    private float temperature; //Значение температуры
    private float humidity; //Значение влажности воздуха
    private float pressure; //Значение давления
    private Observer observer;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void setMeasurements() {
        String pathToSiteWithWeather = "https://www.gismeteo.ru/weather-sochi-5233/now/?ysclid=m7ygx5iind808010376";
        String pathToFileWithHtmlCode = "src/main/resources/data/fileWithHtmlCode.html";
        try {
            Document documentForGetHtmlCode = Jsoup.connect(pathToSiteWithWeather).get();
            htmlCode = String.valueOf(documentForGetHtmlCode);

            FileWriter fileWriter = new FileWriter(pathToFileWithHtmlCode);
            fileWriter.write(String.valueOf(documentForGetHtmlCode));

            Elements elements = documentForGetHtmlCode.select(".item-value");

            String[] linesHtmlCode = String.valueOf(elements).split("\n");

            for (String currentLine : linesHtmlCode) {
                getValuePressure(currentLine.strip());
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public float getValuePressure(String lineHtmlCode) {
        String templateForValuePressure = "pressure-value value=\"";
        float pressure = 0f;

        int leftIndexForValuePressure = lineHtmlCode.indexOf(templateForValuePressure);
        if (leftIndexForValuePressure != -1) {
            leftIndexForValuePressure += templateForValuePressure.length();
            int rightIndexForValuePressure = lineHtmlCode.indexOf("\"", leftIndexForValuePressure);
            String valuePressure = lineHtmlCode.substring(leftIndexForValuePressure, rightIndexForValuePressure);
            System.out.println("Давление \"" + valuePressure + "\"");
            return pressure;
        }
        return pressure;
    }

    public float getValueTemperatureAir() {
        String[] arrayLinesHtmlCode = htmlCode.split("\n");
        String templateForTemperatureAir = "temperatureAir\":[";
        float temperatureAir = 0f;

        for (String lineHtmlCode : arrayLinesHtmlCode) {
            int leftIndexForTemperatureAir = lineHtmlCode.indexOf(templateForTemperatureAir);
            if (leftIndexForTemperatureAir != -1) {
                leftIndexForTemperatureAir += templateForTemperatureAir.length();
                int rightIndexForTemperatureAir = lineHtmlCode.indexOf("]", leftIndexForTemperatureAir);
                String strTemperatureAir = lineHtmlCode.substring(leftIndexForTemperatureAir, rightIndexForTemperatureAir);
                System.out.println(temperatureAir);
                return Float.parseFloat(strTemperatureAir);
            }
        }
        return temperatureAir;
    }

    public float getValueHumidity() {
        String[] arrayLinesHtmlCode = htmlCode.split("\n");
        String templateForHumidity = "Humidity\":[";
        float endHumidity = 0f;

        for (String lineHtmlCode : arrayLinesHtmlCode) {
            int leftIndexForHumidity = lineHtmlCode.indexOf(templateForHumidity);
            if (leftIndexForHumidity != -1) {
                leftIndexForHumidity += templateForHumidity.length();
                int rightIndexForHumidity = lineHtmlCode.indexOf("]", leftIndexForHumidity);
                String humidity = lineHtmlCode.substring(leftIndexForHumidity, rightIndexForHumidity);
                System.out.println(humidity);
                return Float.parseFloat(humidity);
            }
        }
        return endHumidity;
    }


    public void measurementsChanged() {
        float temp = getTemperature();
        float humidity = getHumidity();
        float pressure = getPressure();

    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int indexCurrentObserver = observers.indexOf(observer);
        if (indexCurrentObserver != -1) {
            observers.remove(indexCurrentObserver);
        }
    }

    @Override
    public void notifyObserver() {
        for (int indexCurrentObserver = 0; indexCurrentObserver < observers.size(); indexCurrentObserver++) {
            Observer observer = observers.get(indexCurrentObserver);
            observer.update(temperature, humidity, pressure);
        }
    }
}