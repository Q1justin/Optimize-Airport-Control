public class Disease {
    public String name;
    public double exposureRate;
    public double infectionRate;
    public double recoveryRate;

    public Disease(String inputName, double inputExposureRate, double inputRate, double inputRecoveryRate){
        name = inputName;
        exposureRate = inputExposureRate;
        infectionRate = inputRate;
        recoveryRate = inputRecoveryRate;
    }
}
