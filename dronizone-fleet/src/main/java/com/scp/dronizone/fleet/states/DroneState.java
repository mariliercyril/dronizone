package com.scp.dronizone.fleet.states;

public enum DroneState {
    RECHARGING, // Indisponible car en recharge
    AVAILABLE,  // Prêt à réaliser une livraison
    DELIVERING, // BUSY/en cours de livraison. Busy me semblant trop générique. Sera utile pour US#5
    RETURNING   // todo remove ? Pas sûr du dernier état. C'est plus pour "prouver" que le drone est sur le chemin du (soit car livraison finie, soit car US#5)
}
