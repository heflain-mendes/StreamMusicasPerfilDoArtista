package my.company;

import br.com.interfaces.Musica;
import br.com.interfaces.ReproducaoService;

import java.util.List;
import java.util.Optional;

public class ArtistaService implements br.com.interfaces.ArtistaService {
    private String nome;
    private String bibliografia;
    private String generoMusical;


    @Override
    public Optional<List<Musica>> getMusicasMaisTocadas(String s, ReproducaoService reproducaoService) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Musica>> getDiscografia(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getBiografia(String s) {
        return Optional.empty();
    }

    @Override
    public void atualizarEstatisticasReproducao(String s, ReproducaoService reproducaoService) {

    }
}
