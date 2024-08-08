package Controllers;

import Repositories.BookingRepository;
import Repositories.PaymentRepository;
import Repositories.ShowSeatRepository;
import Services.PricingService;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class PaymentController {
    private Scanner sc = new Scanner(System.in);
    @Autowired
    private PricingService pricingService;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Booking initializePaymentProcess(User loggedInUser, List<ShowSeat> finalSelectedSeatsForBooking) {
        int amount = pricingService.calculatedPrice(finalSelectedSeatsForBooking);
        int response = showPaymentOptions();
        boolean isPaid = requestMoneyTransfer(response, amount);
        if (!isPaid) {
            System.out.println("Transaction failed, unable to book tickets.");
            return null;
        }
        Booking bookedTicket = createTicket(loggedInUser, finalSelectedSeatsForBooking, amount, response);
        return bookedTicket;
    }



    private Booking createTicket(User loggedInUser, List<ShowSeat> finalSelectedSeatsForBooking, int amount, int response) {
        Booking ticket = new Booking();
        ticket.setUser(loggedInUser);
        ticket.setAmount(amount);
        List<Payment> payments = createPayment( amount, response );
        ticket.setPayments( payments);

        changeShowSeatStatus(finalSelectedSeatsForBooking);
        return ticket;
    }


    private List<Payment> createPayment(int amount, int response) {
        Payment receipt =new Payment();
        receipt.setAmount(amount);
        PaymentProvider provider = PaymentProvider.UPI;

        if(response==2){
            provider = PaymentProvider.CARD;
        }else if (response==3){
            provider = PaymentProvider.NET_BANKING;

        }else provider = PaymentProvider.unKnown;
            receipt.setPaymentProvider(provider);
            receipt.setPaymentStatus(PaymentStatus.SUCCESS);

            List<Payment> listOfPayments = new ArrayList<>();
            listOfPayments.add(receipt);
            return listOfPayments;
        }
    private int showPaymentOptions() {
        createSpaceInTerminal(2);
        String optionMessage = "Please select your preferred payment mode\n"
                + "1. Upi \n"
                + "2. Card \n"
                + "3. Net banking";
        System.out.println(optionMessage);
        createSpaceInTerminal(2);
        int response = sc.nextInt();
        return response;
    }


    private void createSpaceInTerminal(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();
        }
    }


    private boolean requestMoneyTransfer(int preferredModeOfPayment, int amount) {
        createSpaceInTerminal(2);
        if(preferredModeOfPayment ==1){
            System.out.println("Intitializing money request via UPI....");
            printMoneyTransactionMessages( amount );
        } else if (preferredModeOfPayment==2) {
            System.out.println("Initializing money request via Card...");
            printMoneyTransactionMessages(amount);
        }else if (preferredModeOfPayment==3){
            System.out.println("Initializing money request via Net Banking...");
            printMoneyTransactionMessages(amount);
            return true;
        }
        return false;
    }

    private void changeShowSeatStatus(List<ShowSeat> showSeats) {
        for( ShowSeat currShowSeat : showSeats ){
            currShowSeat.setShowSeatStatus(ShowSeatStatus.BOOKED);
            showSeatRepository.save( currShowSeat );
        }
    }

    private void printMoneyTransactionMessages(int amount) {
        System.out.println("Payment Successful, received "+ amount);
    }
}
