package com.scp.dronizone.common.states;

public enum DroneState {
    RECHARGING, // Indisponible car en recharge
    AVAILABLE,  // Prêt à réaliser une livraison
    DELIVERING, // ou BUSY, en cours de livraison. Busy me semblant trop générique
    RETURNING   // Pas sûr du dernier état. C'est plus pour "prouver" que le drone est sur le chemin du (soit car livraison finie, soit car US#5)
}
