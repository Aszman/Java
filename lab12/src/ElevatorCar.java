public class ElevatorCar extends Thread{
    int floor=0;

    public int getFloor() {
        return floor;
    }

    enum Tour {UP, DOWN};
    Tour tour = Tour.UP;
    enum Movement {STOP,MOVING};
    Movement movementState = Movement.STOP;


    public void run(){
        for(;;){
            switch(this.movementState){
                case STOP:
                    if(ElevatorStops.get().hasStopBelow(this.floor)){
                        this.movementState = Movement.MOVING;
                        this.tour = Tour.DOWN;
                    }else if(ElevatorStops.get().hasStopAbove(this.floor)){
                        this.movementState = Movement.MOVING;
                        this.tour = Tour.UP;
                    }
                    break;

                case MOVING:
                    switch(this.tour) {
                        case DOWN:
                            --this.floor;
                            while(this.floor > ElevatorStops.get().getMinSetFloor()){
                                if(ElevatorStops.get().whileMovingDownShouldStopAt(this.floor)){
                                    ElevatorStops.get().clearStopDown(this.floor);
                                    System.out.printf("Stayed at floor %d while moving down\n",this.floor);
                                }
                                else{
                                    System.out.printf("Missed floor %d while moving down\n",this.floor);
                                }
                                --this.floor;
                            }
                            ElevatorStops.get().clearStopDown(this.floor);
                            ElevatorStops.get().clearStopUp(this.floor);
                            System.out.printf("Stayed at floor %d while moving down\n",this.floor);

                            this.movementState = Movement.STOP;
                            break;

                        case UP:
                            ++this.floor;
                            while(this.floor < ElevatorStops.get().getMaxSetFloor()){
                                if(ElevatorStops.get().whileMovingUpShouldStopAt(this.floor)) {
                                    ElevatorStops.get().clearStopUp(this.floor);
                                    System.out.printf("Stayed at floor %d while moving up\n", this.floor);
                                }
                                else{
                                    System.out.printf("Missed floor %d while moving up\n",this.floor);
                                }
                                ++this.floor;
                            }
                            ElevatorStops.get().clearStopUp(this.floor);
                            ElevatorStops.get().clearStopDown(this.floor);
                            System.out.printf("Stayed at floor %d while moving up\n", this.floor);

                            this.movementState = Movement.STOP;
                            break;
                    }
                    break;
            }

            System.out.printf("Wait at floor %d\n",this.floor);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}