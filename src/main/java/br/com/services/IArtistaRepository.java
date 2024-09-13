package br.com.services;

import br.com.interfaces.model.IMusica;
import br.com.model.Musica;

import java.util.List;
import java.util.Optional;

public interface IArtistaRepository {
    public Optional<List<IMusica>> getMusicasMaisTocadas(String nomeArtista, int qtd);
    public Optional<List<IMusica>> getMusicas(String nomeArtista);
    public Optional<String> getBiografia(String nomeArtista);
}
