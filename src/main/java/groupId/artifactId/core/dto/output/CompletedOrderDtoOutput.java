package groupId.artifactId.core.dto.output;

import java.time.Instant;
import java.util.List;

public class CompletedOrderDtoOutput {
    private TicketDtoOutput ticket;
    private List<PizzaDtoOutput> items;
    private Long id;
    private Long ticketId;
    private Instant creationDate;
    private Integer version;

    public CompletedOrderDtoOutput() {
    }

    public CompletedOrderDtoOutput(TicketDtoOutput ticket, List<PizzaDtoOutput> items, Long id, Long ticketId,
                                   Instant creationDate, Integer version) {
        this.ticket = ticket;
        this.items = items;
        this.id = id;
        this.ticketId = ticketId;
        this.creationDate = creationDate;
        this.version = version;
    }

    public TicketDtoOutput getTicket() {
        return ticket;
    }

    public void setTicket(TicketDtoOutput ticket) {
        this.ticket = ticket;
    }

    public List<PizzaDtoOutput> getItems() {
        return items;
    }

    public void setItems(List<PizzaDtoOutput> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
