package task_1.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ResponseModel {
    @JsonSetter("response")
    private int id;
    @JsonSetter("countries")
    private List<Country> countryList;
    @JsonSetter("numbers")
    private List<Number> numbers;

    public ResponseModel(int id, List<Country> countryList, List<Number> numbers) {
        this.id = id;
        this.countryList = countryList;
        this.numbers = numbers;
    }

    public ResponseModel() {
    }

    public int getId() {
        return id;
    }

    public List<Country> getCountryList() {
        return countryList;
    }


    public List<Number> getNumbers() {
        return numbers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ResponseModel that = (ResponseModel) o;
        return id == that.id && countryList.equals(that.countryList) && numbers.equals(that.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryList, numbers);
    }
}
