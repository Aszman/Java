import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InternalPanelAgent extends Thread {

    ElevatorCar elevatorCar;
    BlockingQueue<InternalCall> input = new ArrayBlockingQueue<>(100);

    static class InternalCall{
        private final int toFloor;
        InternalCall(int toFloor){
            this.toFloor = toFloor;
        }
    }

    InternalPanelAgent(ElevatorCar elevatorCar){
        this.elevatorCar = elevatorCar;
    }



    public void run(){
        for(;;){
            InternalCall ic = null;

            try{
                ic = input.take();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            if(ic.toFloor == this.elevatorCar.getFloor()){
                continue;
            }else if(ic.toFloor < this.elevatorCar.getFloor()){
                ElevatorStops.get().setLiftStopDown(ic.toFloor);
            }else{
                ElevatorStops.get().setLiftStopUp(ic.toFloor);
            }
        }
    }

}