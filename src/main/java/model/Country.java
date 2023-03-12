package model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Country {
    @JsonSetter("country")
    private int id;
    @JsonSetter("country_text")
    private String name;
}
