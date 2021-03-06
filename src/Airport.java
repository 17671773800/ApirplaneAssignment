import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Airport {

    List<Airplane> listAirplane;
    List<Airplane> listAirplaneGate;
    List<Airplane> listAirplaneRunway;

    public Airport() {
        listAirplane = new LinkedList<Airplane>();
        listAirplaneGate = new LinkedList<Airplane>();
        listAirplaneRunway = new LinkedList<Airplane>();
    }


    public void processInGate(Airplane airplane) {
        long duration = (long) (Math.random() * 2000);

        System.out.println("Thread-ATC : "+getDate()+" Airplane "+airplane.getNum()+" Disembark passenger of Airplane ");
        sleep(duration);
        System.out.println("Thread-ATC : "+getDate()+" Airplane "+airplane.getNum()+" Completed Disembark passenger of Customer in " + duration + " millisecond.");

        System.out.println("Thread-ATC : "+getDate()+" Airplane "+airplane.getNum()+" Refill supplies and fuel of Airplane ");
        duration = (long) (Math.random() * 2000);
        sleep(duration);
        System.out.println("Thread-ATC : "+getDate()+" Airplane "+airplane.getNum()+" Completed Refill supplies of Customer in " + duration + " millisecond.");

        System.out.println("Thread-ATC : "+getDate()+" Airplane "+airplane.getNum()+" embark new passenger of Airplane ");
        duration = (long) (Math.random() * 2000);
        sleep(duration);
        System.out.println("Thread-ATC : "+getDate()+" Airplane "+airplane.getNum()+" Completed embark new passenger of Customer in " + duration + " millisecond.");

        System.out.println("---------------------------------------------------------------------------------------------------");

        sleep(1000);
//        System.out.println("------------------------Customer exits from airplane " + airplane.getNum() + " ...");

        System.out.println("Thread-ATC : "+getDate()+(int)(Math.random() * 50)+" passengers disembarked from the plane");
        System.out.println("Thread-ATC : "+getDate()+(int)(Math.random() * 50)+" passengers boarded the plane");
        System.out.println("---------------------------------------------------------------------------------------------------");

    }


    public void intoGate(Airplane airplane) {

        while (listAirplaneGate.size() >= 4) {

            synchronized (listAirplaneGate) {
                try {
                    System.out.println("Thread-ATC : "+getDate()+" The Gate is occupied, Airplane : " + airplane.getNum() + " waiting for Gate.");
                    listAirplaneGate.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        listAirplaneGate.add(airplane);
        System.out.println("Thread-ATC : "+getDate()+" Airplane : " + airplane.getNum() + " got the Gate. Gate(" + listAirplaneGate.size() + "/4)");
        listAirplaneRunway.remove(airplane);

        sleep(1000);

        processInGate(airplane);

    }

    public void intoRunway(Airplane airplane) {
        System.out.println("Thread-ATC : "+getDate()+": Plane "+airplane.getNum()+": Requesting permission to land!  ");


        while (listAirplaneRunway.size() >= 1 || listAirplaneGate.size() >= 4) {

            synchronized (listAirplaneRunway) {
                try {
                    System.out.println("ATC: Please Airplane " + airplane.getNum() +  " wait and join the circle queue. ");

                    listAirplaneRunway.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


        System.out.println("ATC: allow Airplane "+ airplane.getNum() + "landing runway");
        listAirplaneRunway.add(airplane);
        System.out.println("ATC: Airplane "+ airplane.getNum() + "landing runway success!");
        listAirplane.remove(airplane);

        sleep(1000);
        System.out.println("Thread-ATC : "+getDate()+" entering the runway -- Airplane : " + airplane.getNum() + "  at " + getDate());


    }

    public void leaveAirportFromRunway(Airplane airplane) {

        while (listAirplaneRunway.size() >= 1) {

            synchronized (listAirplaneRunway) {
                try {
                    System.out.println("Thread-ATC : "+getDate()+" The plane can't leave from Gate!The runway is occupied, Airplane : " + airplane.getNum() + " waiting for Runway.");
                    listAirplaneRunway.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        listAirplaneRunway.add(airplane);
        listAirplaneGate.remove(airplane);
        System.out.println("Thread-ATC : "+getDate()+" ------------------------ airplane NUMBER:" + airplane.getNum() + " exits Gate... Gate(" + listAirplaneGate.size() + "/4)");
        System.out.println("Thread-ATC : "+getDate()+" entering the runway -- Airplane : " + airplane.getNum() + " from Gate  at " + getDate());

        System.out.println("Thread-ATC : "+getDate()+" ---------The plane" + airplane.getNum() + " is leaving the runway!----------");


        listAirplaneRunway.remove(airplane);
        System.out.println("Thread-ATC : "+getDate()+" ---------The plane" + airplane.getNum() + " is left the runway success!----------");

        sleep(1000);
    }


    public String getDate(){
        Date date = new Date();
        DateFormat df = DateFormat.getTimeInstance();

        return df.format(date);
    }

    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }
    }

}
