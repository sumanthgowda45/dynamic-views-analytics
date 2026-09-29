package com.sumanth.analytics.model;

import java.time.LocalDate;

public record TicketEvent(
        String ticketId,
        String status,
        String assignee,
        String priority,
        String category,
        LocalDate createdDate,
        long resolutionMinutes
) {}
