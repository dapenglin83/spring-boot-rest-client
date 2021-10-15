package test.springboot.restclient.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CorporateAction {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    Date datePaid;
    String name;
    String anncType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    Date exDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    Date recDate;
    String particulars;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    public Date getDatePaid() {
        return datePaid;
    }

    public String getName() {
        return name;
    }

    public String getAnncType() {
        return anncType;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    public Date getExDate() {
        return exDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    public Date getRecDate() {
        return recDate;
    }

    public String getParticulars() {
        return particulars;
    }
}
