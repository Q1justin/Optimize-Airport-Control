public class Model {
    public State susceptible;
    public State exposed;
    public State infectious;
    public State recovered;

    public Model(Disease disease){
        susceptible = new State("susceptible", disease.exposureRate);
        exposed = new State("exposed", disease.infectionRate);
        infectious = new State("infectious", disease.recoveryRate);
        recovered = new State("recovered");
    }

    public int toExposed(){
        return 0;
    }

    public int toInfected(){
        return 0;
    }

    public int toRecovered(){
        return 0;
    }
}