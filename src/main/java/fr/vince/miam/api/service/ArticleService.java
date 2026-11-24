package fr.vince.miam.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fr.vince.miam.api.dto.ArticleRequest;
import fr.vince.miam.api.dto.ArticleResponse;
import fr.vince.miam.api.exception.ArticleIntrouvableException;
import fr.vince.miam.api.model.Article;
import fr.vince.miam.api.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public List<ArticleResponse> lister() {
        return repository.findAll().stream()
                .map(ArticleResponse::depuis)
                .toList();
    }

    public ArticleResponse ajouter(ArticleRequest demande) {
        Article article = new Article();
        appliquer(demande, article);
        return ArticleResponse.depuis(repository.save(article));
    }

    public ArticleResponse modifier(UUID id, ArticleRequest demande) {
        Article article = repository.findById(id)
                .orElseThrow(() -> new ArticleIntrouvableException(id));
        appliquer(demande, article);
        return ArticleResponse.depuis(repository.save(article));
    }

    public void supprimer(UUID id) {
        if (!repository.existsById(id)) {
            throw new ArticleIntrouvableException(id);
        }
        repository.deleteById(id);
    }

    private void appliquer(ArticleRequest demande, Article article) {
        article.setNom(demande.nom().trim());
        article.setQuantite(demande.quantite());
        article.setAchete(demande.achete());
        article.setCategorie(demande.categorie());
    }
}