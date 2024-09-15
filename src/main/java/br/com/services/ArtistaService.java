package br.com.services;

import br.com.interfaces.model.IMusica;
import br.com.interfaces.repository.IArtistaRepository;
import br.com.interfaces.repository.IMusicaRepository;
import br.com.interfaces.services.IArtistaService;

import java.util.List;
import java.util.Optional;

public class ArtistaService implements IArtistaService {

    private IArtistaRepository artistaRepository;
    private IMusicaRepository  musicaRepository;

    public ArtistaService(IArtistaRepository artistaRepository, IMusicaRepository musicaRepository) {
        this.artistaRepository = artistaRepository;
        this.musicaRepository = musicaRepository;
    }

    @Override
    public Optional<List<IMusica>> getMusicasMaisTocadas(String nomeArtista) {
        return this.musicaRepository.getMusicasMaisTocadas(nomeArtista, 5);
    }

    @Override
    public Optional<List<IMusica>> getMusica(String nomeArtista) {
        return this.musicaRepository.getMusicas(nomeArtista);
    }

    @Override
    public Optional<String> getBiografia(String nomeArtista) {
        return this.artistaRepository.getBiografia(nomeArtista);
    }

    @Override
    public void atualizarEstatisticasReproducao(IMusica musica) {
        this.musicaRepository.atualizarEstatisticasReproducao(musica);
    }
}
