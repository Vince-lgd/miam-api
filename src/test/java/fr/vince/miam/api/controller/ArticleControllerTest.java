package fr.vince.miam.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import fr.vince.miam.api.exception.ArticleIntrouvableException;
import fr.vince.miam.api.service.ArticleService;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArticleService service;

    @Test
    void ajouter_refuseUnNomVide() throws Exception {
        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nom": "   "}
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Données invalides"))
                .andExpect(jsonPath("$.erreurs.nom").value("Le nom de l'article est obligatoire"));

        verify(service, never()).ajouter(any());
    }

    @Test
    void supprimer_renvoie404SiLArticleNExistePas() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new ArticleIntrouvableException(id)).when(service).supprimer(id);

        mockMvc.perform(delete("/articles/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Article introuvable"));
    }
}