package com.scp.dronizone.common.states;

public enum ProcessingState {
    PENDING,    // à payer ? ou à emballer
    PACKED,     // emballé
    DELIVERING, // en cours de livraison
    DELIVERED,  // livré
    CANCELED;   /// todo clarifier ?????? annulé ? ou "demi-tour" car US#5 ?
}
