package it.younify.modules.sgtr.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@code FlagCategory} enum represents the different categories or topics that a thread can be assigned.
 * Each category has a human-readable label associated with it.
 */
@Getter
@AllArgsConstructor
public enum FlagCategory {

    GENERALE("Generale"),
    APPUNTI("Appunti"),
    ESAMI("Esami"),
    CERCO_CASA("Cerco Casa"),
    CERCASI_COINQUILINO("Cercasi Coinquilino"),
    INFO("Informazioni"),
    GRUPPO_STUDIO("Gruppo Studio"),
    SESSIONE("Sessione"),
    VITA_FUORISEDE("Vita da fuorisede"),
    APERITIVO("Aperitivo"),
    NOTTE_ESAME("Notte prima degli esami");

    /**
     * The human-readable label for the category, used for display purposes.
     */
    private final String label;
}
