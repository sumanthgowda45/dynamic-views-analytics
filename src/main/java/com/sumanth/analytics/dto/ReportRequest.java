package com.sumanth.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportRequest {

    @NotBlank
    private String dimension;

    private List<String> metrics = new ArrayList<>(List.of("count", "avgResolutionMinutes"));

    private Map<String, String> filters = Map.of();

    public String getDimension() { return dimension; }
    public void setDimension(String dimension) { this.dimension = dimension; }
    public List<String> getMetrics() { return metrics; }
    public void setMetrics(List<String> metrics) { this.metrics = metrics; }
    public Map<String, String> getFilters() { return filters; }
    public void setFilters(Map<String, String> filters) { this.filters = filters; }
}
