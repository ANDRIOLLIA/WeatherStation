public class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
        currentConditionsDisplay.update(
                weatherData.getValueTemperatureAir(),
                weatherData.getValueHumidity(),
                weatherData.getPressure());
    }
}
