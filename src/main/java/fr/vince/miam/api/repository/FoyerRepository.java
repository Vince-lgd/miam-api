package fr.vince.miam.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.vince.miam.api.model.Foyer;

public interface FoyerRepository extends JpaRepository<Foyer, UUID> {
}