public class Main {

    public static void main(String[] args) {

        System.out.println("Hello world");

        DatabaseManager databaseManager = new DatabaseManager("oac.db");
        databaseManager.addAirport("CVG", 1000);
        databaseManager.addAirports(new String[][]{{"ORD", "4000"},{"JFK", "5000"},{"IAD", "3000"}});
        System.out.println(databaseManager.getAllAirports());
        System.out.println(databaseManager.getAirportByName("CVG"));
        System.out.println(databaseManager.getAirportByName("ORD"));
        System.out.println(databaseManager.getAirportByName("JFK"));
        System.out.println(databaseManager.getAirportByName("IAD"));
        databaseManager.addAirportRoute(0,1,100);
        databaseManager.addAirportRoutes(new String[][]{{"0", "2", "200"},{"0", "3", "150"},{"1", "0", "220"},
                {"1", "2", "304"},{"1", "3", "231"},{"2", "0", "123"}, {"2", "1", "345"}, {"2", "3", "493"},
                {"3", "0", "203"}, {"3", "1", "209"}, {"3", "2", "309"}});
        System.out.println("Get routes from CVG: " + databaseManager.getRoutesFromAirport(0));
        System.out.println("Get routes to ORD: " + databaseManager.getRoutesToAirport(1));
        System.out.println("Get routes involving JFK: " + databaseManager.getAllRoutesForAirport(2));
        System.out.println("Get route between IAD and CVG: " + databaseManager.getRouteBetweenAirports(3, 0));

    }

}
