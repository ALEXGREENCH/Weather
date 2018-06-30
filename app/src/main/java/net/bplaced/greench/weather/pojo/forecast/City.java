
package net.bplaced.greench.weather.pojo.forecast;

public class City {

    private Integer id, population;
    private String name, country;
    private Coord coord;

    public Integer getId() {
        return id;
    }

    public Integer getPopulation() {
        return population;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Coord getCoord() {
        return coord;
    }
}
