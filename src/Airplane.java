import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Airplane implements Runnable {
    int num;

    String type = "";

    Airport airport;

    public Airplane(Airport airport) {
        this.airport = airport;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void run() {
        into();
    }

    public void into() {

        airport.intoRunway(this);

        airport.intoGate(this);

        // airplane completed supply,airplane leave from the runway
        this.setType("completedSupply");
        airport.leaveRunway(this);

    }

}
