package br.com.services;

import br.com.interfaces.model.IMusica;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtistaRepository implements IArtistaRepository {

    @Override
    public Optional<List<IMusica>> getMusicasMaisTocadas(String nomeArtista, int qtd) {
        return Optional.empty();
    }

    @Override
    public Optional<List<IMusica>> getMusicas(String nomeArtista) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getBiografia(String nomeArtista) {
        return Optional.empty();
    }
}
