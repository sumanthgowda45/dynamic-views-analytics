package com.sumanth.analytics.config;

import com.sumanth.analytics.model.TicketEvent;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class SampleData {

    public List<TicketEvent> events() {
        return List.of(
                new TicketEvent("T-1001", "CLOSED", "sumanth", "HIGH", "Platform", LocalDate.of(2026, 1, 4), 120),
                new TicketEvent("T-1002", "OPEN", "qa-team", "MEDIUM", "Search", LocalDate.of(2026, 1, 5), 0),
                new TicketEvent("T-1003", "CLOSED", "sumanth", "HIGH", "Search", LocalDate.of(2026, 1, 6), 240),
                new TicketEvent("T-1004", "IN_PROGRESS", "devops", "LOW", "Infra", LocalDate.of(2026, 1, 7), 0),
                new TicketEvent("T-1005", "CLOSED", "qa-team", "MEDIUM", "Platform", LocalDate.of(2026, 1, 8), 90),
                new TicketEvent("T-1006", "CLOSED", "sumanth", "CRITICAL", "AI", LocalDate.of(2026, 1, 9), 300),
                new TicketEvent("T-1007", "OPEN", "platform-team", "HIGH", "Analytics", LocalDate.of(2026, 1, 10), 0),
                new TicketEvent("T-1008", "RESOLVED", "sumanth", "MEDIUM", "Analytics", LocalDate.of(2026, 1, 11), 150),
                new TicketEvent("T-1009", "CLOSED", "devops", "HIGH", "Infra", LocalDate.of(2026, 1, 12), 180),
                new TicketEvent("T-1010", "CLOSED", "platform-team", "LOW", "Platform", LocalDate.of(2026, 1, 13), 60)
        );
    }
}
