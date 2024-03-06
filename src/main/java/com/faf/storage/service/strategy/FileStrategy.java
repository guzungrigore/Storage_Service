package com.faf.storage.service.strategy;

public interface FileStrategy {
    byte[] generateFile();
    boolean supports(String format);
}