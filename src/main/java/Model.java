public class Model {
    State susceptible;
    State exposed;
    State infectious;
    State recovered;
    public Model(State... args){
        susceptible = new State("Susceptible", 0.3);
        exposed = new State("Exposed", 0.3);
        infectious = new State("Infectious", 0.3);
        recovered = new State("Recovered", 0.3);
    }

}