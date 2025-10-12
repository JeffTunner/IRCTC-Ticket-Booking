package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;

public class UserBookingService {

    private User user;

    private List<User> userList;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        File users = new File(USERS_PATH);
        try {
            userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
            System.out.println("DEBUG: Successfully loaded " + userList.size() + " users.");
        } catch (Exception e) {
            System.out.println("DEBUG: Error while reading JSON file:");
            e.printStackTrace();
        }
    }

    public User loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.orElse(null);
    }

    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void fetchBooking() {
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if(userFetched.isPresent()) {
            userFetched.get().printTickets();
        }
    }

    public boolean cancelBooking(String ticketId) throws IOException {

        if(ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be empty!");
            return Boolean.FALSE;
        }

        String finalTicketId1 = ticketId;
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));
        if(removed) {
            saveUserListToFile();
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        } else {
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train) {
        if(train.getSeats() == null){
            return new ArrayList<>();
        }
        return train.getSeats();
    }

    public boolean bookTrainSeat(Train train, int row, int seat, String source, String destination, String dateOfTravel) {
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if(row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if(seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);

                    Ticket ticket = new Ticket(UUID.randomUUID().toString(), user.getUserId(), source, destination, dateOfTravel, train);
                    user.getTicketsBooked().add(ticket);
                    saveUserListToFile();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }
}
