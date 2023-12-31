package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, boolean discount) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        long inMillisecond = ticket.getInTime().getTime();
        long outMillisecond = ticket.getOutTime().getTime();
        long durationMillisecond = outMillisecond - inMillisecond;
        float durationHourDecimal = ((float) durationMillisecond / (60 * 60 * 1000));

        if (durationHourDecimal < 0.5) {
            ticket.setPrice(0);
            System.out.println("c'est gratuit");
        } else {
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(durationHourDecimal * Fare.CAR_RATE_PER_HOUR);
                    if (discount){ticket.setPrice(ticket.getPrice() * 0.95 );}
                    break;
                }
                case BIKE: {
                    ticket.setPrice(durationHourDecimal * Fare.BIKE_RATE_PER_HOUR);
                    if (discount){ticket.setPrice(ticket.getPrice() * 0.95 );}
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
    }
    public void calculateFare(Ticket ticket) {
        calculateFare(ticket, false);
    }
}