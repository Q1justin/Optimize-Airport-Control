public class Airport {
    int susceptiblePop;
    int exposedPop;
    int infectedPop;
    int recoveredPop;
    Model model;

    public Airport(int inputSusceptible, int inputExposure, int inputInfected, int inputRecovered, Disease disease){
        susceptiblePop = inputSusceptible;
        exposedPop = inputExposure;
        infectedPop = inputInfected;
        recoveredPop = inputRecovered;
        model = new Model(disease);
    }

    public void setSusceptiblePop(int susceptiblePop){
        this.susceptiblePop = susceptiblePop;
    }

    public void setExposedPop(int exposedPop){
        this.exposedPop = exposedPop;
    }

    public void setInfectedPop(int infectedPop){
        this.infectedPop = infectedPop;
    }

    public void setRecoveredPop(int recoveredPop){
        this.recoveredPop = recoveredPop;
    }

    public int getPopulationPop(){
        return this.susceptiblePop;
    }

    public int getExposedPop(){
        return this.exposedPop;
    }

    public int getInfectedPop(){
        return this.infectedPop;
    }

    public int getRecoveredPop(){
        return this.recoveredPop;
    }

    public int getTotalPop(){
        return this.susceptiblePop + this.exposedPop + this.infectedPop + this.recoveredPop;
    }
}