package com.uca.parcialfinalncapas.exceptions.ticket;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
