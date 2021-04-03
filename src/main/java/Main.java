public class Main {

    public static void main(String[] args) {
        Disease selectedDisease = new Disease("covid19", 0.3, 0.4, 0.5);
        Airport a = new Airport(2000, 1000, 500, 250, selectedDisease);
    }
}
