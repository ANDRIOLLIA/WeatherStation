public class StatisticsDisplay implements Observer, DisplayElement {

    @Override
    public void display() {
        System.out.println("Статистика!");
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {

    }
}