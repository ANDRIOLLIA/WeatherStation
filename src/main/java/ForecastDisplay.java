import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.ArrayList;

public class ForecastDisplay implements Observer, DisplayElement {

    public ArrayList<String> getDaysAndMinMaxValuesTemperature() {
        ArrayList<String> listDaysAndMinMaxValuesTemperature = new ArrayList<>();
        String urlSiteWeather10days = "https://www.gismeteo.ru/weather-sochi-5233/10-days/";
        String pathToFileWithHtmlCode = "data/htmlSiteForWeather10days.html";
        try {
            Document document = Jsoup.connect(urlSiteWeather10days).get();
            String htmlCodeText = String.valueOf(document);
            FileWriter fileWriter = new FileWriter(pathToFileWithHtmlCode);
            fileWriter.write(htmlCodeText);

            //Для дней
            Elements elementsForDays = document.select(".day");
            String templateForDay = "<div class=\"day\">\n ";
            for (int indexForDay = 0; indexForDay < 10; indexForDay++) {
                Element currentElement = elementsForDays.get(indexForDay);
                String strCurrentElement = String.valueOf(currentElement);
                int leftIndexForDay = strCurrentElement.indexOf(templateForDay);
                if (leftIndexForDay != -1) {
                    leftIndexForDay += templateForDay.length();
                    int rightIndexForDay = strCurrentElement.indexOf("\n<", leftIndexForDay);
                    String day = strCurrentElement.substring(leftIndexForDay, rightIndexForDay);
                    //Для минимальной температуры
                    Elements elementsForMinTemperature = document.select(".mint");
                    String templateForMinTemperature = "<div class=\"mint\">\n <temperature-value value=\"";
                    for (int indexForMinTemperature = 0; indexForMinTemperature < 10; indexForMinTemperature++) {
                        Element currentElementForMinTemperature = elementsForMinTemperature.get(indexForMinTemperature);
                        String strCurrentElementForMinTemperature = String.valueOf(currentElementForMinTemperature);
                        int leftIndexForDayForMinTemperature = strCurrentElementForMinTemperature.indexOf(templateForMinTemperature);
                        if (leftIndexForDayForMinTemperature != -1) {
                            leftIndexForDayForMinTemperature += templateForMinTemperature.length();
                            int rightIndexForDayForMinTemperature = strCurrentElementForMinTemperature.indexOf("\"", leftIndexForDayForMinTemperature);
                            String minTemperature = strCurrentElementForMinTemperature.substring(leftIndexForDayForMinTemperature, rightIndexForDayForMinTemperature);
                            //Для максимальной температуры
                            Elements elementsForMaxTemperature = document.select(".maxt");
                            String templateForMaxTemperature = "<div class=\"maxt\">\n <temperature-value value=\"";
                            for (int indexForMaxTemperature = 0; indexForMaxTemperature < 10; indexForMaxTemperature++) {
                                Element currentElementForMaxTemperature = elementsForMaxTemperature.get(indexForMaxTemperature);
                                String strCurrentElementForMaxTemperature = String.valueOf(currentElementForMaxTemperature);
                                int leftIndexForDayForMaxTemperature = strCurrentElementForMaxTemperature.indexOf(templateForMaxTemperature);
                                if (leftIndexForDayForMaxTemperature != -1) {
                                    leftIndexForDayForMaxTemperature += templateForMaxTemperature.length();
                                    int rightIndexForDayForMaxTemperature = strCurrentElementForMaxTemperature.indexOf("\"", leftIndexForDayForMaxTemperature);
                                    String maxTemperature = strCurrentElementForMaxTemperature.substring(leftIndexForDayForMaxTemperature, rightIndexForDayForMaxTemperature);
                                    if (indexForDay == indexForMinTemperature &&
                                            indexForDay == indexForMaxTemperature &&
                                            indexForMinTemperature == indexForMaxTemperature) {
                                        String currentDayAndMinMaxTemperature =
                                                "Текущий день \"" + day + "\"" +
                                                        "; текущая минимальная температура \"" + minTemperature + "\"; " +
                                                        "текущая максимальная температура \"" + maxTemperature + "\"";

                                        listDaysAndMinMaxValuesTemperature.add(currentDayAndMinMaxTemperature);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
        return listDaysAndMinMaxValuesTemperature;
    }

    @Override
    public void display() {
        System.out.println("Прогноз:");
        for (String dayAndMinMaxTemperature : getDaysAndMinMaxValuesTemperature()) {
            System.out.println(dayAndMinMaxTemperature);
        }
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {

    }
}