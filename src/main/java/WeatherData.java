import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
    private ArrayList<Observer> observers;
    //Температура
    private float temperature;
    //Влажность
    private float humidity;
    //Давление
    private float pressure;

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public WeatherData() {
        observers = new ArrayList<>();
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements() {
        String urlSiteWeather = "https://www.gismeteo.ru/weather-sochi-5233/now/";
        String pathToFileWithHtmlCode = "data/htmlSiteForWeather.html";
        try {
            Document document = Jsoup.connect(urlSiteWeather).get();
            String htmlCodeText = String.valueOf(document);
            FileWriter fileWriter = new FileWriter(pathToFileWithHtmlCode);
            fileWriter.write(htmlCodeText);


            String[] linesHtmlCode = htmlCodeText.split("\n");
            for (String currentLineHtmlCode : linesHtmlCode) {
                currentLineHtmlCode = currentLineHtmlCode.strip();
                getValuePressure(currentLineHtmlCode);
                getValueHumidity(currentLineHtmlCode);
                getValueTemperature(currentLineHtmlCode);
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
        measurementsChanged();
    }

    public void getValuePressure(String line) {
        String templateForPressure = "<pressure-value value=\"";
        int leftIndexForPressure = line.indexOf(templateForPressure);
        if (leftIndexForPressure != -1) {
            leftIndexForPressure += templateForPressure.length();
            int rightIndexForPressure = line.indexOf("\"", leftIndexForPressure);
            String pressureValue = line.substring(leftIndexForPressure, rightIndexForPressure);
            pressure = Float.parseFloat(pressureValue);
        }
    }

    public void getValueHumidity(String line) {
        String templateForHumidity = "\"humidity\":[";
        int leftIndexForHumidity = line.indexOf(templateForHumidity);
        if (leftIndexForHumidity != -1) {
            leftIndexForHumidity += templateForHumidity.length();
            int rightIndexForHumidity = line.indexOf("]", leftIndexForHumidity);
            String humidityValue = line.substring(leftIndexForHumidity, rightIndexForHumidity);
            humidity = Float.parseFloat(humidityValue);
        }
    }

    public void getValueTemperature(String line) {
        String templateForTemperature = "\"temperatureAir\":[";
        int leftIndexForTemperature = line.indexOf(templateForTemperature);
        if (leftIndexForTemperature != -1) {
            leftIndexForTemperature += templateForTemperature.length();
            int rightIndexForTemperature = line.indexOf("]", leftIndexForTemperature);
            String temperatureValue = line.substring(leftIndexForTemperature, rightIndexForTemperature);
            temperature = Float.parseFloat(temperatureValue);
        }
    }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int indexObserverInList = observers.indexOf(observer);
        if (indexObserverInList >= 0) {
            observers.remove(indexObserverInList);
        }
    }

    @Override
    public void notifyObservers() {
        for (int indexCurrentObserver = 0; indexCurrentObserver < observers.size(); indexCurrentObserver++) {
            Observer observer = observers.get(indexCurrentObserver);
            observer.update(temperature, humidity, pressure);
        }
    }
}