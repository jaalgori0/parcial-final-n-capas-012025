package com.uca.parcialfinalncapas.controller;

import com.uca.parcialfinalncapas.dto.request.TicketCreateRequest;
import com.uca.parcialfinalncapas.dto.request.TicketUpdateRequest;
import com.uca.parcialfinalncapas.dto.response.GeneralResponse;
import com.uca.parcialfinalncapas.dto.response.TicketResponse;
import com.uca.parcialfinalncapas.exceptions.ticket.BadTicketRequestException;
import com.uca.parcialfinalncapas.service.TicketService;
import com.uca.parcialfinalncapas.utils.ResponseBuilderUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {
    private TicketService ticketService;

    @PreAuthorize("hasRole('TECH')")
    @GetMapping
    public ResponseEntity<GeneralResponse> getAllTickets() {
        return ResponseBuilderUtil.buildResponse("Tickets obtenidos correctamente",
                ticketService.getAllTickets().isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK,
                ticketService.getAllTickets());
    }

    @PreAuthorize("hasRole('TECH') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getTicketById(@PathVariable Long id) {
        TicketResponse ticket = ticketService.getTicketById(id);
        if (ticket == null) {
            throw new BadTicketRequestException("Ticket no encontrado");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();


        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            if (!ticket.getCorreoSolicitante().equals(correo)) {
                return ResponseBuilderUtil.buildResponse("No autorizado para ver este ticket", HttpStatus.FORBIDDEN, null);
            }
        }

        return ResponseBuilderUtil.buildResponse("Ticket encontrado", HttpStatus.OK, ticket);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<GeneralResponse> createTicket(@Valid @RequestBody TicketCreateRequest ticket) {
        TicketResponse createdTicket = ticketService.createTicket(ticket);
        return ResponseBuilderUtil.buildResponse("Ticket creado correctamente", HttpStatus.CREATED, createdTicket);
    }

    @PreAuthorize("hasRole('TECH')")
    @PutMapping
    public ResponseEntity<GeneralResponse> updateTicket(@Valid @RequestBody TicketUpdateRequest ticket) {
        TicketResponse updatedTicket = ticketService.updateTicket(ticket);
        return ResponseBuilderUtil.buildResponse("Ticket actualizado correctamente", HttpStatus.OK, updatedTicket);
    }

    @PreAuthorize("hasRole('TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseBuilderUtil.buildResponse("Ticket eliminado correctamente", HttpStatus.OK, null);
    }
}
