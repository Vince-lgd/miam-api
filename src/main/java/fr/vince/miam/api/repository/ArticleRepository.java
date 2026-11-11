package fr.vince.miam.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.vince.miam.api.model.Article;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}