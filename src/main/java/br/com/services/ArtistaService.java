package br.com.services;

import br.com.interfaces.model.IMusica;
import br.com.interfaces.services.IArtistaService;
import br.com.interfaces.services.IReproducaoService;

import java.util.List;
import java.util.Optional;

public class ArtistaService implements IArtistaService {

    private ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = new ArtistaRepository();
    }

    @Override
    public Optional<List<IMusica>> getMusicasMaisTocadas(String nomeArtista) {
        return this.artistaRepository.getMusicasMaisTocadas(nomeArtista, 5);
    }

    @Override
    public Optional<List<IMusica>> getMusica(String nomeArtista) {
        return this.artistaRepository.getMusicas(nomeArtista);
    }

    @Override
    public Optional<String> getBiografia(String nomeArtista) {
        return this.artistaRepository.getBiografia(nomeArtista);
    }


    //Esperar grupo rafa/gabriel
    @Override
    public void atualizarEstatisticasReproducao(String s, IReproducaoService iReproducaoService) {
    }


}
