package task1.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Country {
    @JsonSetter("country")
    private int id;
    @JsonSetter("country_text")
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Country country = (Country) o;
        return id == country.id && name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
