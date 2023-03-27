package task_1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Number {
    @JsonSetter("full_number")
    private String fullNumber;

    public Number(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    public Number() {
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public void setFullNumber(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    @Override
    public String toString() {
        return fullNumber;
    }
}
