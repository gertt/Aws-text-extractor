package com.example.awstextextractor.model;

import java.util.List;
public record CustomResponse(boolean success, String message, List<String> data) { }
