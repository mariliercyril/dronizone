package com.scp.dronizone.fleet.states;

public enum DroneState {
    RECHARGING, // Indisponible car en recharge
    AVAILABLE,  // Prêt à réaliser une livraison
    DELIVERING, // BUSY/en cours de livraison. Busy me semblant trop générique. Sera utile pour US#5
    FORCED_RETURNING //,   // rappel forcé, avant que la livraison ne soit faite (US5) --> ne doit pas supprimer l'ID de livraison associé
}
