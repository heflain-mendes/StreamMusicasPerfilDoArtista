package my.company.interfaces;

import br.com.interfaces.IMusica;
import br.com.interfaces.IUsuario;

import java.util.List;
import java.util.Optional;

public interface IArtistaService {
    Optional<List<IMusica>> getMusicasMaisTocadas(String artista,
                                                 ReproducaoService reproducaoService);
    Optional<List<IMusica>> getMusicas(String artista);
    Optional<String> getBiografia(String artista);
    void atualizarEstatisticasReproducao(String artista,
                                         IReproducaoService reproducaoService);
}
