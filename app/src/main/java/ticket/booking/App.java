
package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.utils.UserServiceUtil;

import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Train Booking System");
        Scanner sc = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        try {
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            System.out.println("There is Something Wrong!");
            return;
        }

        Train trainSelectedForBooking = null;
        boolean trainSelected = false;
        String source = null;
        String destination = null;

        while (option!=7) {
            System.out.println("Please choose from the options below what you want to do: ");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = sc.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = sc.next();
                    User userToSignup = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashedPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                    boolean isSignUp = userBookingService.signUp(userToSignup);
                    if(isSignUp) {
                        System.out.println("You have successfully Signed  Up!");
                        System.out.println("Please Login to continue for further requests.");
                    } else {
                        System.out.println("Sign Up Failed!");
                    }
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = sc.next();
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = sc.next();
                    User tempUser = new User(nameToLogin, passwordToLogin, UserServiceUtil.hashedPassword(passwordToLogin), new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(tempUser);
                        User loggedInUser = userBookingService.loginUser();
                        if(loggedInUser!=null) {
                            userBookingService.setUser(loggedInUser);
                            System.out.println("Log In Successful!!!");
                            System.out.println("Please choose further options according to your requirements.");
                        } else {
                            System.out.println("Invalid username or Password!");
                        }
                    } catch (IOException e) {
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings...");
                    userBookingService.fetchBooking();
                    break;
                case 4:
                    System.out.println("Type your source station");
                    source = sc.next();
                    System.out.println("Type your destination station");
                    destination = sc.next();
                    List<Train> trains = userBookingService.getTrains(source, destination);
                    if(trains.isEmpty()){
                        System.out.println("No trains available.");
                        break;
                    }
                    int index = 1;
                    for (Train t: trains){
                        System.out.println(index+" Train id : "+t.getTrainId());
                        for (Map.Entry<String, String> entry: t.getStationTimes().entrySet()){
                            System.out.println("station "+entry.getKey()+" time: "+entry.getValue());
                        }
                        index++;
                    }
                    System.out.println("Select a train by typing 1,2,3...");
                    int selectedIndex = sc.nextInt() - 1;
                    if(selectedIndex < 0 || selectedIndex >= trains.size()){
                        System.out.println("Invalid selection!");
                        break;
                    }
                    trainSelectedForBooking = trains.get(selectedIndex);
                    trainSelected = true;
                    break;
                case 5:
                    if (trainSelectedForBooking == null) {
                        System.out.println("Please search and select a train first!");
                        break;
                    }
                    System.out.println("Select a seat out of these seats: ");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                    for (List<Integer> row: seats){
                        for (Integer val: row){
                            System.out.print(val+" ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    System.out.println("Enter the row");
                    int row = sc.nextInt();
                    System.out.println("Enter the column");
                    int col = sc.nextInt();
                    System.out.println("Booking your seat...");
                    String dateOfTravel = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row, col, source, destination, dateOfTravel);
                    if(booked.equals(Boolean.TRUE)) {
                        System.out.println("Booked! Enjoy your journey!!!");
                    } else {
                        System.out.println("Can't book this seat!");
                    }
                    break;
                case 6:
                    System.out.println("Select to cancel your bookings...");
                    System.out.println("Enter the ticket ID to cancel: ");
                    String ticketId = sc.next();
                    userBookingService.cancelBooking(ticketId);
                    break;
                default:
                    System.out.println("Please select only the options given to you.");
                    break;
            }
        }
    }
}
