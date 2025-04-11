public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private float pressure;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    @Override
    public void display() {
        System.out.println("Текущее состояние:");
        System.out.println("Температура \"" + temperature + "\"");
        System.out.println("Давление \"" + pressure + "\"");
        System.out.println("Влажность \"" + humidity + "\"\n");
    }

}