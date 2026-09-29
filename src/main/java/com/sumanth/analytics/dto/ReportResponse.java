package com.sumanth.analytics.dto;

import java.util.List;

public record ReportResponse(
        String dimension,
        List<String> metrics,
        int totalEvents,
        List<ReportRow> rows
) {}
