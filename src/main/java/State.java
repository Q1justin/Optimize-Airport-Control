public class State{
    String stateName;
    double rate;

    public State(String inputName, double inputRate){
        stateName = inputName;
        rate = inputRate;
    }

    public static void main(String[] args) {
        State state = new State("Covid", 0.3);
        System.out.println(state.stateName);
        System.out.println(state.rate);
    }
}
