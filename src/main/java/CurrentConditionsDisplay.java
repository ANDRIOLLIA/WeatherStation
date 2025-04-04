public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature; //Значение температуры
    private float humidity; //Значение влажности воздуха
    private Subject weatherDate; //Переменная для данных о погоде

    public CurrentConditionsDisplay(Subject weatherDate) {
        this.weatherDate = weatherDate;
        weatherDate.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature
                + "C degrees and " + humidity + "% humidity");
    }
}
