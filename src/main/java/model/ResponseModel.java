package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseModel {
    @JsonSetter("response")
    private int id;
    @JsonSetter("countries")
    private List<Country> countryList;
    @JsonSetter("numbers")
    private List<Number> numbers;
}
