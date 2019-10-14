package com.scp.dronizone.common.states;

public enum DroneBatteryState {
    FULL,
    OK,     // principal
    LOW,    // principal, c'est celui-ci qu'Elena utilisera pour déterminer si elle doit ordonner au Drone de se recharger (ça veut dire qu'elle sait exactement ce que "LOW" signifire...)
    EMPTY   // todo remove ?
}
