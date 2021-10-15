package test.springboot.restclient.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {

    @JsonProperty(index = 3)
    @JsonAlias("o")
    double open;
    @JsonProperty(index = 4)
    @JsonAlias("h")
    double high;
    @JsonProperty(index = 5)
    @JsonAlias("l")
    double low;
    @JsonProperty(index = 0)
    @JsonAlias("lt")
    double last;
    @JsonProperty(index = 1)
    @JsonAlias("b")
    double bid;
    @JsonProperty(index = 2)
    @JsonAlias("s")
    double ask;

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getLast() {
        return last;
    }
}
