public class Elevator {
    static ElevatorCar car = new ElevatorCar();
    static ExternalPanelsAgent externalPanelAgent = new ExternalPanelsAgent(car);
    static InternalPanelAgent internalPanelAgent = new InternalPanelAgent(car);

    // symulacja przywołania windy z panelu zewnętrznego
    static void makeExternalCall(int atFloor,boolean directionUp){
        try {
            externalPanelAgent.input.put(new ExternalPanelsAgent.ExternalCall(atFloor,directionUp));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // symulacja wyboru pietra w panelu wewnętrznym
    static void makeInternalCall(int toFloor){
        try {
            internalPanelAgent.input.put(new InternalPanelAgent.InternalCall(toFloor));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // uruchomienie wątków
    static void init(){
        car.start();
        externalPanelAgent.start();
        internalPanelAgent.start();
    }

    // miesjce na kod testowy
    public static void main(String[] args) {
        test3();
    }

    private static void test1(){
        init();
        makeExternalCall(6,false);

        shortenSleep(2000);

        makeInternalCall(2);
    }

    private static void test2(){
        init();
        makeExternalCall(3,false);

        shortenSleep(2000);

        makeInternalCall(0);

        shortenSleep(2000);

        makeInternalCall(7);

        shortenSleep(2000);

    }

    private static void test3(){
        init();

        makeExternalCall(3,true);

        shortenSleep(500);

        makeExternalCall(5,false);
        makeInternalCall(7);

        shortenSleep(1000);

        makeInternalCall(1);

        makeExternalCall(9, false);

        shortenSleep(2000);

        makeInternalCall(0);
    }

    private static void shortenSleep(int millis){
        try {
            Thread.currentThread().sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}