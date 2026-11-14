package fr.vince.miam.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fr.vince.miam.api.exception.ArticleIntrouvableException;
import fr.vince.miam.api.model.Article;
import fr.vince.miam.api.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public List<Article> lister() {
        return repository.findAll();
    }

    public Article ajouter(Article article) {
        return repository.save(article);
    }

    public Article modifier(UUID id, Article modifications) {
        Article article = repository.findById(id)
                .orElseThrow(() -> new ArticleIntrouvableException(id));

        article.setNom(modifications.getNom());
        article.setQuantite(modifications.getQuantite());
        article.setAchete(modifications.isAchete());
        article.setCategorie(modifications.getCategorie());

        return repository.save(article);
    }

    public void supprimer(UUID id) {
        if (!repository.existsById(id)) {
            throw new ArticleIntrouvableException(id);
        }
        repository.deleteById(id);
    }
}