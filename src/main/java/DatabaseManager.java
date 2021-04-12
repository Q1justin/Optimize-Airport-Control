import java.sql.*;
import java.util.Dictionary;

public class DatabaseManager {

    private String databasePath;
    private Connection connection;

    private boolean valid;

    public DatabaseManager (String databasePath) {
        this.databasePath = databasePath;
        connection = null;
        valid = false;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            statement = connection.createStatement();
            statement.setQueryTimeout(30); // from tutorial, seems like a good go-to

            // probably don't want to drop them every time ultimately
            statement.executeUpdate("DROP TABLE IF EXISTS Airports");
            statement.executeUpdate("DROP TABLE IF EXISTS AirportRoutes");

            statement.executeUpdate("CREATE TABLE Airports (airportId_int INTEGER PRIMARY KEY, airportName_text STRING, population_int INTEGER, isControlled_bool INTEGER)");
            statement.executeUpdate("CREATE TABLE AirportRoutes (airportRouteId_int INTEGER PRIMARY KEY, sourceAirportId_int INTEGER, destinationAirportId_int INTEGER, travelersPerDay_int INTEGER)");

            valid = true;
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    private int executeUpdates (String... updates) {
        int successfulExecutions = 0;

        Statement statement = null;

        try {

            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            for (String update : updates) {
                // I'm not sure if this is the most efficient way to do it. I think this executes each query as you
                // give it; there might be a way to queue them up together and then run them all at once.
                System.out.println("Executing " + update);
                if (update != null && update.length() > 0) {
                    statement.executeUpdate(update);
                    successfulExecutions++;
                }
            }

        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e);
            }
        }

        return successfulExecutions;
    }

    public boolean isValid() {
        return valid;
    }

    // we may want a better way to pass in data than a nested string array, idk what though
    // we could have two parallel arrays of String airportName and Integer population, but then you have to
    // keep them in sync when composing them -- not sure which is better
    public int addAirports (String[][] airports) {
        String[] updates = new String[airports.length];
        int updateIndex = 0;
        for (int i = 0; i < updates.length; i++) {
            String airportName = airports[i][0];
            int population;
            try {
                population = Integer.parseInt(airports[i][1]);
                updates[updateIndex] = "INSERT INTO Airports (airportName_text, population_int, isControlled_bool) VALUES ('" + airportName + "', " + population + ", 0)";
                updateIndex++;
            }
            catch (NumberFormatException e) {
                System.err.println(e);
            }

        }
        return executeUpdates(updates);
    }

    public boolean addAirport (String airportName, int population) {
        return executeUpdates("INSERT INTO Airports (airportName_text, population_int, isControlled_bool) VALUES ('" + airportName + "', " + population + ", 0)") == 1;
    }

    // in the future, we want to return an Airport class object
    private String getAirports (String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        String result = "";
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            System.out.println("Executing " + query);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result += "(";
                result += resultSet.getInt("airportId_int");
                result += ", ";
                result += resultSet.getString("airportName_text");
                result += ", ";
                result += resultSet.getInt("population_int");
                result += ", ";
                result += resultSet.getInt("isControlled_bool");
                result += ")\n";
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e);
            }
        }
        return result;
    }

    public String getAllAirports() {
        return getAirports("SELECT * FROM Airports");
    }

    public String getAirportById (int airportId) {
        return getAirports("SELECT * FROM Airports WHERE airportId_int = " + airportId);
    }

    public String getAirportByName (String airportName) {
        return getAirports("SELECT * FROM Airports WHERE airportName_text = '" + airportName + "'");
    }

    public int addAirportRoutes (String[][] airportRoutes) {
        String[] updates = new String[airportRoutes.length];
        int updateIndex = 0;
        for (int i = 0; i < updates.length; i++) {
            int sourceAirportId;
            int destinationAirportId;
            int travelersPerDay;
            try {
                sourceAirportId = Integer.parseInt(airportRoutes[i][0]);
                destinationAirportId = Integer.parseInt(airportRoutes[i][1]);
                travelersPerDay = Integer.parseInt(airportRoutes[i][2]);
                if ((sourceAirportId >= 0 && destinationAirportId >= 0) && sourceAirportId != destinationAirportId) {
                    updates[updateIndex] = "INSERT INTO AirportRoutes (sourceAirportId_int, destinationAirportId_int, travelersPerDay_int) VALUES (" + sourceAirportId + ", " + destinationAirportId + ", " + travelersPerDay + ")";
                    updateIndex++;
                }
            }
            catch (NumberFormatException e) {
                System.err.println(e);
            }
        }
        return executeUpdates(updates);
    }

    public boolean addAirportRoute (int sourceAirportId, int destinationAirportId, int travelersPerDay) {
        return ((sourceAirportId >= 0 && destinationAirportId >= 0) && sourceAirportId != destinationAirportId) &&
                executeUpdates("INSERT INTO AirportRoutes (sourceAirportId_int, destinationAirportId_int, travelersPerDay_int) VALUES (" + sourceAirportId + ", " + destinationAirportId + ", " + travelersPerDay + ")") == 1;
    }

    // again, in the future we will want this to be an AirportRoute class, or something
    public String getAirportRoutes (String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        String result = "";
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result += "(";
                result += resultSet.getInt("airportRouteId_int");
                result += ", ";
                result += resultSet.getInt("sourceAirportId_int");
                result += ", ";
                result += resultSet.getInt("destinationAirportId_int");
                result += ", ";
                result += resultSet.getInt("travelersPerDay_int");
                result += ")\n";
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e);
            }
        }
        return result;
    }

    public String getAllAirportRoutes() {
        return getAirportRoutes("SELECT * FROM AirportRoutes");
    }

    public String getRoutesFromAirport (int sourceAirportId) {
        return getAirportRoutes("SELECT * FROM AirportRoutes WHERE sourceAirportId_int = " + sourceAirportId);
    }

    public String getRoutesToAirport(int destinationAirportId) {
        return getAirportRoutes("SELECT * FROM AirportRoutes WHERE destinationAirportId_int = " + destinationAirportId);
    }

    public String getAllRoutesForAirport (int airportId) {
        return getAirportRoutes("SELECT * FROM AirportRoutes WHERE sourceAirportId_int = " + airportId + " OR destinationAirportId_int = " + airportId);
    }

    public String getRouteBetweenAirports (int sourceAirportId, int destinationAirportId) {
        return getAirportRoutes("SELECT * FROM AirportRoutes WHERE sourceAirportId_int = " + sourceAirportId + " AND destinationAirportId_int = " + destinationAirportId);
    }

    // sample code based on https://github.com/xerial/sqlite-jdbc
    // we probably need to provide attribution for using this library

    /*public static void testDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set query timeout to 30s

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values (1, 'leo')");
            statement.executeUpdate("insert into person values (2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        }
        catch (SQLException e) {
            // if the error message is "out of memory", it probably means no database file found
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }*/

}
