package app.cars;

import lombok.Data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Message implements Serializable
{
    private final String text;
    private final String date;

    public Message(String text)
    {
        this.text = text;

        Date date = new Date();
        String strDate = "yyyy-mm-dd hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDate);

        this.date = dateFormat.format(date);
    }
}
