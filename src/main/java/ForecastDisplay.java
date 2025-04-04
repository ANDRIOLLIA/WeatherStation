import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.ArrayList;

public class ForecastDisplay implements Observer {

    public ArrayList<String> getListTemperatureFor10Days(){

        String pathToSiteWithWeather = "https://www.gismeteo.ru/weather-sochi-5233/10-days/";
        String pathToFileWithHtmlCode = "src/main/resources/data/fileWithHtmlCodeFor10Days.html";

        ArrayList<String> listTemperatureFor10Days = new ArrayList<>();

        try{
            Document document = Jsoup.connect(pathToSiteWithWeather).get();
            String strHtlCode = String.valueOf(document);

            FileWriter fileWriter = new FileWriter(pathToFileWithHtmlCode);
            fileWriter.write(strHtlCode);

            Elements element = document.select(".day");
            String templateForDays = "<div class=\"day\">\n ";
            for(Element element1 : element){
                String strElement = String.valueOf(element1);
                int leftIndexForDays = strElement.indexOf(templateForDays);
                if (leftIndexForDays != -1) {
                    leftIndexForDays += templateForDays.length();
                    int rightIndexForDays = strElement.indexOf("\n</div>", leftIndexForDays);
                    String day = strElement.substring(leftIndexForDays, rightIndexForDays);
                    System.out.println(day);
                }
            }

            Elements elementForMinTemp = document.select(".mint");
            String templateForMinTemp = "<temperature-value value=\">";
            for(int i = 0; i < 10; i++){
                String strElement = String.valueOf(i);
                int leftIndexForMinTemp = strElement.indexOf(templateForMinTemp);
                if (leftIndexForMinTemp != -1) {
                    leftIndexForMinTemp += templateForMinTemp.length();
                    int rightIndexForMinTemp = strElement.indexOf("\n</div>", leftIndexForMinTemp);
                    String minTemp = strElement.substring(leftIndexForMinTemp, rightIndexForMinTemp);
                    System.out.println(minTemp);
                }
            }
        }catch (Exception ex){
            ex.getMessage();
        }
        return listTemperatureFor10Days;

    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
    }
}
