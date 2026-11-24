package fr.vince.miam.api.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.vince.miam.api.exception.ArticleIntrouvableException;
import fr.vince.miam.api.model.Article;
import fr.vince.miam.api.model.Categorie;
import fr.vince.miam.api.repository.ArticleRepository;
import fr.vince.miam.api.dto.ArticleRequest;
import fr.vince.miam.api.dto.ArticleResponse;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository repository;

    @InjectMocks
    private ArticleService service;

    @Test
    void supprimer_leveUneExceptionSiLArticleNExistePas() {
        // Arrange : on prépare la situation
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        // Act + Assert : on appelle la méthode et on vérifie le résultat
        assertThatThrownBy(() -> service.supprimer(id))
                .isInstanceOf(ArticleIntrouvableException.class);

        // On vérifie aussi que rien n'a été supprimé
        verify(repository, never()).deleteById(id);
    }

    @Test
    void modifier_leveUneExceptionSiLArticleNExistePas() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.modifier(id, new ArticleRequest("Lait", 1, false, Categorie.ALIMENTATION)))
                .isInstanceOf(ArticleIntrouvableException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void modifier_recopieLesNouvellesValeursEtEnregistre() {
        // Arrange : un article existe déjà en base
        UUID id = UUID.randomUUID();
        Article existant = new Article();
        existant.setNom("Lait");
        when(repository.findById(id)).thenReturn(Optional.of(existant));
        when(repository.save(any(Article.class))).thenAnswer(appel -> appel.getArgument(0));

        ArticleRequest demande = new ArticleRequest("Lait d'avoine", 2, true, Categorie.ALIMENTATION);

        // Act
        ArticleResponse resultat = service.modifier(id, demande);

        // Assert
        assertThat(resultat.nom()).isEqualTo("Lait d'avoine");
        assertThat(resultat.quantite()).isEqualTo(2);
        assertThat(resultat.achete()).isTrue();
        assertThat(resultat.categorie()).isEqualTo(Categorie.ALIMENTATION);
        verify(repository).save(existant);
    }

    @Test
    void supprimer_supprimeLArticleSIlExiste() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.supprimer(id);

        verify(repository).deleteById(id);
    }
}