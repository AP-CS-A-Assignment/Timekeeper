package timer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public class TimePiece extends ScheduledService<Void> {

    private Timer timer;
    private SimpleStringProperty stringProperty;

    int hours, mins, secs;

    public TimePiece(Timer _timer) {
        super();
        setPeriod(Duration.millis(1000));

        timer = _timer;

        hours = mins = secs = 0;


    }

    public TimePiece(SimpleStringProperty string) {
        super();
        setPeriod(Duration.millis(1000));

        stringProperty = new SimpleStringProperty();

        string.bind(stringProperty);

        hours = mins = secs = 0;

        AtomicInteger a = new AtomicInteger(1);




    }


    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                stringProperty.set("" + hours-- + " "  + mins-- + " "+ secs--);
                System.out.println(hours);
                return null;
            }
        };
    }
}
