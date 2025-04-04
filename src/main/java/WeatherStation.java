public class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        weatherData.setMeasurements();


        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
        currentConditionsDisplay.update(weatherData.getValueTemperatureAir(), weatherData.getHumidity(), weatherData.getPressure());

        ForecastDisplay forecastDisplay = new ForecastDisplay();
        forecastDisplay.getListTemperatureFor10Days();
    }
}
