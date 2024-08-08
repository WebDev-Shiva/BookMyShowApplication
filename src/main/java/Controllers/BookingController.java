package Controllers;

import Repositories.MovieRepository;
import Repositories.ShowRepository;
import Repositories.ShowSeatRepository;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class BookingController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private PaymentController paymentController;

    private Scanner sc = new Scanner(System.in);
    private Map<Integer, ShowSeat> showSeatsLocalCacheMap = new HashMap<>();

    public Booking bookMovie(User loggedInUser) {
        Movie selectedMovie = displayMoviesForSelection();
        Show showRunningSelectedMovie = showRepository.findByMovie(selectedMovie);
        List<ShowSeat> seatsInThisShow = fetchAllSeatsInShow(showRunningSelectedMovie);

        List<ShowSeat> finalSelectedSeatsForBooking = new ArrayList<>();
        boolean moveToPaymentSection = false;
        boolean isUserDiscardedSelection = false;

        while (!moveToPaymentSection) {
            int seatsAvailableCount = displaySeatsForSelection(seatsInThisShow);
            finalSelectedSeatsForBooking = selectSeatsForBooking(seatsAvailableCount);

            if (finalSelectedSeatsForBooking.size() == 0) { // EDGE CASE HANDLING
                isUserDiscardedSelection = true;
                break;
            }
            boolean isSeatSelectionFinal = BlockedSeatFinalVerification(finalSelectedSeatsForBooking);

            if (!isSeatSelectionFinal) { // If user makes wrong selection, he'll get another chance to select :
                revertSelectedSeatStatus(finalSelectedSeatsForBooking);
                moveToPaymentSection = false;
            } else moveToPaymentSection = true;
        }

        Booking bookedTicket = null;
        if (!isUserDiscardedSelection && moveToPaymentSection) {
            bookedTicket = paymentController.initializePaymentProcess(loggedInUser, finalSelectedSeatsForBooking);
        }
        return bookedTicket;
    }

    public void createSpaceInTerminal(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();
        }
    }

    public List<ShowSeat> fetchAllSeatsInShow(Show show) {
        return showSeatRepository.findAllByShow(show);
    }

    public Movie displayMoviesForSelection() {
        System.out.println("------------SHOWS CURRENTLY RUNNING-----------------------------");
        createSpaceInTerminal(2);

        System.out.println("Choose the movie");
        List<Movie> movies = movieRepository.findAll();
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + " : " + movies.get(i).getName());
        }
        int movieSelection = sc.nextInt();
        return movies.get(movieSelection - 1);
    }

    public int displaySeatsForSelection(List<ShowSeat> showSeats) {
        System.out.println(" Select seats from the below layout ");
        createSpaceInTerminal(3); // creates 3 lines in the terminal :

        int idx = 0; // to get elements from the showSeats list :
        int availableSeatCount = 0;

        for (int row = 0; row < 3; row++) { // I'm hardcoding the size of rows & cols
            for (int col = 0; col < 4; col++) {
                ShowSeat currShowSeat = showSeats.get(idx++);
                int seatNo = currShowSeat.getSeat().getSeatNo();
                showSeatsLocalCacheMap.put(seatNo, currShowSeat);

                ShowSeatStatus currShowSeatStatus = currShowSeat.getShowSeatStatus();
                if (currShowSeatStatus.equals(ShowSeatStatus.AVAILABLE)) {
                    availableSeatCount++;
                }

                String seatDetails = "--seat " + seatNo + " : " + currShowSeatStatus + "---";
                System.out.print(seatDetails);
            }
            createSpaceInTerminal(2); // creates 2 lines in the terminal.
        }
        return availableSeatCount;
    }

    private List<ShowSeat> selectSeatsForBooking(int seatsAvailableCount) {
        boolean isSelectionDone = false;
        List<ShowSeat> selectedShowSeats = new ArrayList<>();

        while (seatsAvailableCount > 0 && !isSelectionDone) {
            createSpaceInTerminal(2);
            String selectionMessage = "Do you want to select Seats?\n" +
                    "0. No \n" +
                    "1. Yes";
            System.out.println(selectionMessage);
            createSpaceInTerminal(2);
            int response = sc.nextInt();

            if (response == 1) {
                System.out.println("Please enter the seat number here");
                int seatNo = sc.nextInt();

                if (!showSeatsLocalCacheMap.containsKey(seatNo)) {
                    System.out.println("Please enter a valid seat number");
                    continue;
                }
                ShowSeat currSelectedSeat = showSeatsLocalCacheMap.get(seatNo);
                if (currSelectedSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                    seatsAvailableCount--;
                    selectedShowSeats.add(currSelectedSeat);
                    currSelectedSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);

                    System.out.println("Seat No " + seatNo + " is blocked for booking, remaining available seats " + seatsAvailableCount);
                    createSpaceInTerminal(2);
                } else {
                    System.out.println("Sorry, the selected seat is not available");
                }
            } else isSelectionDone = true;
        }
        showSeatsLocalCacheMap.clear(); // once the selection is done, we clear the cache :
        return selectedShowSeats;
    }

    private boolean BlockedSeatFinalVerification(List<ShowSeat> finalSelectedSeatsForBooking) {
        createSpaceInTerminal(2);
        System.out.println("Selected Seat count = " + finalSelectedSeatsForBooking.size()
                + ". Please find the detailed list below");

        for (int i = 0; i < finalSelectedSeatsForBooking.size(); i++) {
            ShowSeat currShowSeat = finalSelectedSeatsForBooking.get(i);
            createSpaceInTerminal(1);
            System.out.println((i + 1) + ". s.No: " + currShowSeat.getSeat().getSeatNo());
        }

        createSpaceInTerminal(2);

        String confirmationMessage = "Please select yes if the blocked seats are correct\n"
                + "0. No\n"
                + "1. Yes";

        System.out.println(confirmationMessage);
        int response = sc.nextInt();
        return response == 1;
    }

    private void revertSelectedSeatStatus(List<ShowSeat> finalSelectedSeatsForBooking) {
        for (ShowSeat currShowSeat : finalSelectedSeatsForBooking) {
            currShowSeat.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
        }
    }
}
