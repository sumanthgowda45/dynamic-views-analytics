package com.sumanth.analytics.service;

import com.sumanth.analytics.config.SampleData;
import com.sumanth.analytics.dto.ReportRequest;
import com.sumanth.analytics.dto.ReportResponse;
import com.sumanth.analytics.dto.ReportRow;
import com.sumanth.analytics.model.TicketEvent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private static final Set<String> DIMENSIONS = Set.of("status", "assignee", "priority", "category");
    private static final Set<String> METRICS = Set.of("count", "avgResolutionMinutes");

    private final SampleData sampleData;

    public ReportService(SampleData sampleData) {
        this.sampleData = sampleData;
    }

    public ReportResponse generate(ReportRequest request) {
        String dimension = request.getDimension().trim().toLowerCase(Locale.ROOT);
        if (!DIMENSIONS.contains(dimension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unsupported dimension. Allowed: " + DIMENSIONS);
        }

        List<String> metrics = request.getMetrics() == null || request.getMetrics().isEmpty()
                ? List.of("count", "avgResolutionMinutes")
                : request.getMetrics().stream().map(m -> m.toLowerCase(Locale.ROOT)).toList();

        for (String metric : metrics) {
            if (!METRICS.contains(metric)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Unsupported metric: " + metric + ". Allowed: " + METRICS);
            }
        }

        List<TicketEvent> filtered = sampleData.events().stream()
                .filter(event -> matchesFilters(event, request.getFilters()))
                .toList();

        Map<String, List<TicketEvent>> grouped = filtered.stream()
                .collect(Collectors.groupingBy(event -> dimensionValue(event, dimension), LinkedHashMap::new, Collectors.toList()));

        List<ReportRow> rows = grouped.entrySet().stream()
                .map(entry -> new ReportRow(entry.getKey(), computeMetrics(entry.getValue(), metrics)))
                .sorted(Comparator.comparing(ReportRow::dimensionValue))
                .toList();

        return new ReportResponse(dimension, metrics, filtered.size(), rows);
    }

    public Map<String, Object> metadata() {
        return Map.of(
                "dimensions", DIMENSIONS.stream().sorted().toList(),
                "metrics", METRICS.stream().sorted().toList(),
                "filterKeys", List.of("status", "assignee", "priority", "category")
        );
    }

    private boolean matchesFilters(TicketEvent event, Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return true;
        }
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            String key = filter.getKey().toLowerCase(Locale.ROOT);
            String expected = filter.getValue();
            if (expected == null || expected.isBlank()) {
                continue;
            }
            String actual = switch (key) {
                case "status" -> event.status();
                case "assignee" -> event.assignee();
                case "priority" -> event.priority();
                case "category" -> event.category();
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown filter: " + key);
            };
            if (!actual.equalsIgnoreCase(expected.trim())) {
                return false;
            }
        }
        return true;
    }

    private String dimensionValue(TicketEvent event, String dimension) {
        return switch (dimension) {
            case "status" -> event.status();
            case "assignee" -> event.assignee();
            case "priority" -> event.priority();
            case "category" -> event.category();
            default -> "unknown";
        };
    }

    private Map<String, Object> computeMetrics(List<TicketEvent> events, List<String> metrics) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (String metric : metrics) {
            if ("count".equals(metric)) {
                result.put("count", events.size());
            } else if ("avgresolutionminutes".equals(metric) || "avgResolutionMinutes".equalsIgnoreCase(metric)) {
                double avg = events.stream()
                        .mapToLong(TicketEvent::resolutionMinutes)
                        .filter(v -> v > 0)
                        .average()
                        .orElse(0);
                result.put("avgResolutionMinutes", Math.round(avg * 100.0) / 100.0);
            }
        }
        return result;
    }
}
