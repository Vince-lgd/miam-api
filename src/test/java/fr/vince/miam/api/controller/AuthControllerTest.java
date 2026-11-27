package fr.vince.miam.api.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import fr.vince.miam.api.service.AuthService;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    private String inscription(String motDePasse) {
        return """
                {
                  "email": "vince@test.fr",
                  "motDePasse": "%s",
                  "nomFoyer": "Notre foyer"
                }
                """.formatted(motDePasse);
    }

    @Test
    void inscription_renvoie409SansDetailsTechniques() throws Exception {
        when(authService.inscrire(any())).thenThrow(
                new DataIntegrityViolationException("duplicate key value violates unique constraint uk_email"));

        mockMvc.perform(post("/auth/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inscription("courses du samedi")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflit"))
                .andExpect(content().string(not(containsString("constraint"))));
    }

    @Test
    void inscription_refuseUnMotDePasseTropCourt() throws Exception {
        mockMvc.perform(post("/auth/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inscription("court")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erreurs.motDePasse")
                        .value("Le mot de passe doit contenir au moins 12 caractères"));

        verify(authService, never()).inscrire(any());
    }

    @Test
    void inscription_refuseUnMotDePasseDePlusDe72Octets() throws Exception {
        String motDePasse = "é".repeat(40);

        mockMvc.perform(post("/auth/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inscription(motDePasse)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erreurs.motDePasse").value("Le mot de passe est trop long"));

        verify(authService, never()).inscrire(any());
    }
}