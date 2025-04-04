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
            getValueHumidity();
            getValueTemperatureAir();

        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public float getValuePressure(String lineHtmlCode) {
        String templateForValuePressure = "pressure-value value=\"";
        float pressure = 0;
        int leftIndexForValuePressure = lineHtmlCode.indexOf(templateForValuePressure);
        if (leftIndexForValuePressure != -1) {
            leftIndexForValuePressure += templateForValuePressure.length();
            int rightIndexForValuePressure = lineHtmlCode.indexOf("\"", leftIndexForValuePressure);
            String valuePressure = lineHtmlCode.substring(leftIndexForValuePressure, rightIndexForValuePressure);
            pressure = Float.parseFloat(valuePressure);
            this.pressure = pressure;
            return pressure;
        }
        return pressure;
    }

    public float getValueTemperatureAir() {
        String[] arrayLinesHtmlCode = htmlCode.split("\n");
        String templateForTemperatureAir = "temperatureAir\":[";
        float temperatureAir = 0;

        for (String lineHtmlCode : arrayLinesHtmlCode) {
            int leftIndexForTemperatureAir = lineHtmlCode.indexOf(templateForTemperatureAir);
            if (leftIndexForTemperatureAir != -1) {
                leftIndexForTemperatureAir += templateForTemperatureAir.length();
                int rightIndexForTemperatureAir = lineHtmlCode.indexOf("]", leftIndexForTemperatureAir);
                String strTemperatureAir = lineHtmlCode.substring(leftIndexForTemperatureAir, rightIndexForTemperatureAir);
                temperatureAir = Float.parseFloat(strTemperatureAir);
                return temperatureAir;
            }
        }
        return temperatureAir;
    }

    public float getValueHumidity() {
        String[] arrayLinesHtmlCode = htmlCode.split("\n");
        String templateForTemperatureAir = "humidity\":[";
        float humidity = 0;

        for (String lineHtmlCode : arrayLinesHtmlCode) {
            int leftIndexForHumidity = lineHtmlCode.indexOf(templateForTemperatureAir);
            if (leftIndexForHumidity != -1) {
                leftIndexForHumidity += templateForTemperatureAir.length();
                int rightIndexForHumidity = lineHtmlCode.indexOf("]", leftIndexForHumidity);
                String strHumidity = lineHtmlCode.substring(leftIndexForHumidity, rightIndexForHumidity);
                humidity = Float.parseFloat(strHumidity);
                this.humidity = humidity;
                return humidity;
            }
        }
        return humidity;
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
