public class WeatherData implements Subject{
    private float temperature; //Значение температуры
    private float humidity; //Значение влажности воздуха
    private float pressure; //Значние давление

    private CurrentConditionsDisplay currentConditionsDisplay; //Экран с текущими данными погоды

    private StatisticsDisplay statisticsDisplay; //Экран статистики погоды

    private ForecastDisplay forecastDisplay; //Экран прогноза погоды

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
    public void registerObserver() {

    }

    @Override
    public void removeObserver() {

    }

    @Override
    public void notifyObserver() {

    }
}
