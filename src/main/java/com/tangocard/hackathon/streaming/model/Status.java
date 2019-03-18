package com.tangocard.hackathon.streaming.model;

public enum Status {
    CREATED, PENDING, PROCESSING, COMPLETE, FAILED;

    public static Status getNextStatus(Status currentStatus) {
        int ordinal = currentStatus.ordinal();
        return Status.values()[(ordinal + 1) % 5];
    }
}
