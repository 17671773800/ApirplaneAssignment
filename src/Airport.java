import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
        System.out.println("Thread-ATC : "+getDate()+" Disembark passenger of Airplane : " + airplane.getNum());
        System.out.println("Thread-ATC : "+getDate()+" Refill supplies and fuel of Airplane : " + airplane.getNum());
        System.out.println("Thread-ATC : "+getDate()+" embark new passenger of Airplane : " + airplane.getNum());
        System.out.println("---------------------------------------------------------------------------------------------------");
        long duration = (long) (Math.random() * 60);

        try {
            Thread.sleep(duration);
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }
        System.out.println("Thread-ATC : "+getDate()+" Completed Disembark passenger of Customer : " + airplane.getNum() + " in " + duration + " seconds.");
        System.out.println("Thread-ATC : "+getDate()+" Completed Refill supplies of Customer : " + airplane.getNum() + " in " + duration + " seconds.");
        System.out.println("Thread-ATC : "+getDate()+" Completed embark new passenger of Customer : " + airplane.getNum() + " in " + duration + " seconds.");
//        System.out.println("------------------------Customer exits from airplane " + airplane.getNum() + " ...");

        System.out.println("Thread-ATC : "+getDate()+(int)(Math.random() * 50)+"位乘客下了飞机");
        System.out.println("Thread-ATC : "+getDate()+(int)(Math.random() * 50)+"位乘客登录上了飞机");
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

        try {
            Thread.sleep((long) (Math.random() * 20));
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }

        processInGate(airplane);

    }

    public void intoRunway(Airplane airplane) {
        System.out.println("Thread-ATC : "+getDate()+": Plane "+airplane.getNum()+": Requesting permission to land!  ");


        while (listAirplaneRunway.size() >= 1 || listAirplaneGate.size() >= 4) {

            synchronized (listAirplaneRunway) {
                try {
//                    if(listAirplaneGate.size() >= 4){
//                        System.out.println("All the Gate is occupied, Airplane : " + airplane.getNum() + " don't into Runway.");
//                    }else if(listAirplaneRunway.size() >= 1 ){
//                        System.out.println("The runway is occupied, Airplane : " + airplane.getNum() + " waiting for Runway.");
//                    }
                    System.out.println("ATC: Please Airplane " + airplane.getNum() +  "wait and join the circle queue. ");

                    listAirplaneRunway.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


        System.out.println("ATC: 允许Airplane "+ airplane.getNum() + "降落runway");
        listAirplaneRunway.add(airplane);

        listAirplane.remove(airplane);

        try {
            Thread.sleep((long) (Math.random() * 60));
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }

        System.out.println("Thread-ATC : "+getDate()+" entering the runway -- Airplane : " + airplane.getNum() + "  at " + getDate());


    }

    public void leaveRunway(Airplane airplane) {

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

        try {
            Thread.sleep((long) (Math.random() * 60));
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }

    }


    public String getDate(){
        Date date = new Date();
        DateFormat df = DateFormat.getTimeInstance();

        return df.format(date);
    }

}
