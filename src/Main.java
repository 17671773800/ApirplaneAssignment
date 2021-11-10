import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {

        Airport airport = new Airport();
        int airplaneNum = 10;

        for (int i = 1; i <= airplaneNum; i++) {


            Airplane airplane = new Airplane(airport);
            Thread thAirplane = new Thread(airplane);
            airplane.setNum(i);

            airport.listAirplane.add(airplane);
            thAirplane.start();

//            try {
//                // airplane into time
//                TimeUnit.SECONDS.sleep((long) (Math.random() * 20));
//
//            } catch (InterruptedException iex) {
//                iex.printStackTrace();
//            }
        }

        Thread t1 = new Thread("notifyRunway") {
            public void run() {
                while (true) {
                    try {
                        // airplane into time
                        Thread.sleep((long) (Math.random() * 20));
                    } catch (InterruptedException iex) {
                        iex.printStackTrace();
                    }

                    synchronized (airport.listAirplaneRunway) {
                        if (airport.listAirplane.size() == 0 && airport.listAirplaneGate.size() == 0) {
                            break;
                        }
                        if (airport.listAirplaneRunway.size() < 1) {
                            airport.listAirplaneRunway.notify();

                        }
                    }

                }
            }
        };
        t1.start();


        Thread t2 = new Thread("notifyGate") {
            public void run() {
                while (true) {
                    try {
                        // airplane into time
                        Thread.sleep((long) (Math.random() * 20));

                    } catch (InterruptedException iex) {
                        iex.printStackTrace();
                    }

                    synchronized (airport.listAirplaneGate) {
                        if (airport.listAirplane.size() == 0 && airport.listAirplaneRunway.size() == 0) {
                            break;
                        }
                        if (airport.listAirplaneGate.size() < 4) {
                            airport.listAirplaneGate.notify();

                        }
                    }

                }
            }
        };
        t2.start();
    }

}
