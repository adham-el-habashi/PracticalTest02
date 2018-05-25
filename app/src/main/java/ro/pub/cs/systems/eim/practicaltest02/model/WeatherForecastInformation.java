package ro.pub.cs.systems.eim.practicaltest02.model;

public class WeatherForecastInformation {
    private String resp =  null;

    public WeatherForecastInformation(String resp) {
        this.resp = resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getResp() {
        return this.resp;
    }
}