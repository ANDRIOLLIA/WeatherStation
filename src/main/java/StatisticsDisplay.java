import java.util.ArrayList;

public class StatisticsDisplay implements Observer, DisplayElement {

    private ForecastDisplay forecastDisplay;
    public double averageValueAllTemperatureAir;
    private ArrayList<Double> listAllSortedTemperaturesAir;

    public StatisticsDisplay(ForecastDisplay forecastDisplay) {
        this.forecastDisplay = forecastDisplay;
        averageValueAllTemperatureAir = 0;
        listAllSortedTemperaturesAir = new ArrayList<>();
    }

    public double getAverageValueAllTemperatureAir() {
        double sum = 0;
        for (double currentAverageTemperature : forecastDisplay.getListAllTemperatureAir()) {
            sum += currentAverageTemperature;
        }
        int countTemperaturesAir = forecastDisplay.getListAllTemperatureAir().size();
        averageValueAllTemperatureAir = sum / countTemperaturesAir;
        System.out.println("Ср. знач. температур: " + averageValueAllTemperatureAir);
        return averageValueAllTemperatureAir;
    }

    public void sortedAllTemperatureAir() {
        listAllSortedTemperaturesAir = forecastDisplay.getListAllTemperatureAir();
        int i = 0;
        int j = listAllSortedTemperaturesAir.size() - 1;
        /*
        ВГАБ
        АБВГ
        */
        for (; i < listAllSortedTemperaturesAir.size() - 2; i++) {
            for (; j >= (i + 1); j--) {
                System.out.println("Итерация № " + i);
                System.out.println("i " + listAllSortedTemperaturesAir.get(i));
                System.out.println("j " + listAllSortedTemperaturesAir.get(j));
                double temp = listAllSortedTemperaturesAir.get(i);
                if (listAllSortedTemperaturesAir.get(i).compareTo(listAllSortedTemperaturesAir.get(j)) > 0) {
                    listAllSortedTemperaturesAir.set(i, listAllSortedTemperaturesAir.get(j));
                    listAllSortedTemperaturesAir.set(j, temp);
                }
            }
            j = forecastDisplay.getListAllTemperatureAir().size() - 1;
        }
        System.out.println("Сортированный список всех температур: " + listAllSortedTemperaturesAir);
    }

    @Override
    public void display() {
        System.out.println("Статистика!");
        System.out.println("Ср. знач. для всех температур: " + averageValueAllTemperatureAir);
    }

    public void sortedAllTemperaturesAir() {

    }

    @Override
    public void update(float temperature, float humidity, float pressure) {

    }
}