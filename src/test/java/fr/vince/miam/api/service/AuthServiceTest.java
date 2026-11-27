package fr.vince.miam.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.vince.miam.api.dto.InscriptionRequest;
import fr.vince.miam.api.exception.EmailDejaUtiliseException;
import fr.vince.miam.api.model.Utilisateur;
import fr.vince.miam.api.repository.FoyerRepository;
import fr.vince.miam.api.repository.UtilisateurRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UtilisateurRepository utilisateurs;

    @Mock
    private FoyerRepository foyers;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService service;

    @Test
    void inscrire_enregistreLeMotDePasseChiffre() {
        InscriptionRequest demande = new InscriptionRequest(" Vince@Test.fr ", "motdepasse123", "Notre foyer");
        when(passwordEncoder.encode("motdepasse123")).thenReturn("empreinte-chiffree");

        service.inscrire(demande);

        ArgumentCaptor<Utilisateur> captureur = ArgumentCaptor.forClass(Utilisateur.class);
        verify(utilisateurs).save(captureur.capture());
        Utilisateur enregistre = captureur.getValue();

        assertThat(enregistre.getMotDePasse()).isEqualTo("empreinte-chiffree");
        assertThat(enregistre.getEmail()).isEqualTo("vince@test.fr");
    }

    @Test
    void inscrire_refuseUnEmailDejaUtilise() {
        // 1. Préparer : une demande d'inscription
        InscriptionRequest demande = new InscriptionRequest("vince@test.fr", "motdepasse123", "Notre foyer");

        // 2. Programmer le mock : l'e-mail existe déjà
        when(utilisateurs.existsByEmail("vince@test.fr")).thenReturn(true);

        // 3. Agir et vérifier : inscrire doit lever l'exception
        assertThatThrownBy(() -> service.inscrire(demande))
                .isInstanceOf(EmailDejaUtiliseException.class);

        // 4. Vérifier que rien n'a été enregistré
        verify(foyers, never()).save(any());
        verify(utilisateurs, never()).save(any());
    }
}