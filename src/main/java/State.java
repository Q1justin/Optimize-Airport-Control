public class State{
    public String name;
    public double transitionRate;

    /*For last State in the Model that doesn't need a transitionRate*/
    public State(String inputName){
        name = inputName;
    }

    public State(String inputName, double inputRate){
        name = inputName;
        transitionRate = inputRate;
    }

    public void setName(String name){
        this.name =  name;
    }

    public void setTransitionRate(double transitionRate){
        this.transitionRate =  transitionRate;
    }

    public String getName(){
        return this.name;
    }

    public double getTransitionRate(){
        return this.transitionRate;
    }
}
