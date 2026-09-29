package com.sumanth.analytics.dto;

import java.util.Map;

public record ReportRow(String dimensionValue, Map<String, Object> metrics) {}
