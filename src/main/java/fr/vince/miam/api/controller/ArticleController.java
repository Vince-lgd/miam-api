package fr.vince.miam.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import fr.vince.miam.api.model.Article;
import fr.vince.miam.api.repository.ArticleRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository repository;

    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Article> lister() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Article ajouter(@Valid @RequestBody Article article) {
        return repository.save(article);
    }

    @PutMapping("/{id}")
    public Article modifier(@PathVariable UUID id, @Valid @RequestBody Article modifications) {
        Article article = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        article.setNom(modifications.getNom());
        article.setQuantite(modifications.getQuantite());
        article.setAchete(modifications.isAchete());
        article.setCategorie(modifications.getCategorie());

        return repository.save(article);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
}