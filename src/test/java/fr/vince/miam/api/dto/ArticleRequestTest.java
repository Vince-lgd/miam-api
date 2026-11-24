package fr.vince.miam.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import fr.vince.miam.api.model.Categorie;

class ArticleRequestTest {

    @Test
    void valeursParDefautSiChampsAbsents() {
        ArticleRequest demande = new ArticleRequest("Lait", null, null, null);

        assertThat(demande.nom()).isEqualTo("Lait");
        assertThat(demande.quantite()).isEqualTo(1);
        assertThat(demande.achete()).isFalse();
        assertThat(demande.categorie()).isEqualTo(Categorie.AUTRE);
    }
}