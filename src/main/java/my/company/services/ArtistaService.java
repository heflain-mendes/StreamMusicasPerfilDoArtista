package my.company.services;

import br.com.interfaces.IArtista;
import br.com.interfaces.IMusica;
import my.company.interfaces.IArtistaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtistaService implements IArtistaService {
    private List<IArtista> artistas;

    public ArtistaService() {
        this.artistas = new ArrayList<>();
    }

    @Override
    public Optional<List<IMusica>> getMusicasMaisTocadas(String artista, IReproducaoService reproducaoService) {
        return this.artistas
                .stream()
                .filter(item -> item.getNome().equalsIgnoreCase(artista))
                .map(IArtista::getMusicasMaisTocadas)
                .findFirst()
                .orElseGet(Optional::empty);
    }

    @Override
    public Optional<List<IMusica>> getMusicas(String artista) {
        return this.artistas
                .stream()
                .filter(item -> item.getNome().equalsIgnoreCase(artista))
                .map(IArtista::getDiscografia)
                .findFirst();
    }

    @Override
    public Optional<String> getBiografia(String artista) {
        return this.artistas
                .stream()
                .filter(item -> item.getNome().equalsIgnoreCase(artista))
                .map(IArtista::getBiografia)
                .findFirst();
    }

    @Override
    public void atualizarEstatisticasReproducao(String artista, IReproducaoService reproducaoService) {
        Optional<IArtista> artista = this.artistas
                .stream()
                .filter(item -> item.getNome().equalsIgnoreCase(artista))
                .findFirst();



    }
}
