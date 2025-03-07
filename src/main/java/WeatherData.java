import java.util.ArrayList;

public class WeatherData implements Subject{
    private float temperature; //Значение температуры
    private float humidity; //Значение влажности воздуха
    private float pressure; //Значние давление

    private ArrayList<Observer> observers;

    private Observer observer;

    public WeatherData(){
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

    public void update(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void measurementsChanged(){
        float temp = getTemperature();
        float humidity = getHumidity();
        float pressure = getPressure();
    }


    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObserver() {

    }
}
