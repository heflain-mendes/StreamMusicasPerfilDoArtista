package br.com.tests;

import br.com.interfaces.model.IMusica;
import br.com.interfaces.services.IArtistaService;
import br.com.model.Musica;
import br.com.repositories.ArtistaRepository;
import br.com.repositories.MusicaRepository;
import br.com.services.ArtistaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArtistaServiceRealTest {
    public static IArtistaService artistaService;

    @BeforeAll()
    public static void setUp(){
        artistaService =
            new ArtistaService(
                ArtistaRepository.getArtistaRepository(),
                MusicaRepository.getMusicaRepository()
            );
    }

    @Test
    public void verificaRetornoSeNaoExistirArtista(){
        Optional<String> biografia = artistaService.getBiografia("Hiago Moreira");
        Optional<List<IMusica>> musicas = artistaService.getMusicas("Hiago Moreira");
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas("Hiago Moreira");

        assertFalse(biografia.isPresent(), "Biografia está presente");
        assertFalse(musicas.isPresent(), "Musicas estão presente");
        assertFalse(musicasMaisTocadas.isPresent(), "Musicas mais tocadas estão presente");
    }

    @Test
    public void verificaRetornoBibliografiaTest(){
        Optional<String> biografia = artistaService.getBiografia("Bon Jovi");

        assertTrue(biografia.isPresent(), "Biografia NÃO está presente");
    }

    @Test
    public void verificaRetornoMusicas(){
        Optional<List<IMusica>> listaMusica = artistaService.getMusicas("Bon Jovi");
        assertTrue(listaMusica.isPresent(), "Musicas NÃO estão presente");
    }

    @Test
    public void verificaRetornoMusicasMaisTocadas(){
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas("Bon Jovi");
        assertTrue(musicasMaisTocadas.isPresent(), "Musicas mais tocadas NÃO estão presente");
    }

    @Test
    public void verificaAtualizacaoReproducaoTest() throws Exception{
        var musica = new Musica("Numb", "Linkin Park", "Nu Metal", 3.5);

        artistaService.atualizarEstatisticasReproducao(musica);
    }

    @Test
    public void verificarAtualizacaoReproducaoArtistaInexistenteTest(){
        var musica = new Musica("Hiago Moreira", "Linkin Park", "Nu Metal", 3.5);

        //When
        Exception exception = assertThrows(Exception.class , () ->{
            artistaService.atualizarEstatisticasReproducao(musica);
        });

        assertEquals("Artista " + musica.getArtista() + " não existe na plataforma", exception.getMessage());
    }
}
