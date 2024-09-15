package br.com.tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.interfaces.model.IMusica;
import br.com.interfaces.repository.IArtistaRepository;
import br.com.interfaces.repository.IMusicaRepository;
import br.com.model.Artista;
import br.com.services.ArtistaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;


public class ArtistaServiceTest {

    @Mock
    private IArtistaRepository artistaRepository;

    @Mock
    private IMusicaRepository musicaRepository;

    @InjectMocks
    private ArtistaService artistaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.artistaService = new ArtistaService(artistaRepository, musicaRepository);
    }

    @Test
    public void verificaRetornoSeNaoExistirArtista(){
        //Given: Artista mockado no repositorio
        when(artistaRepository.getBiografia("Hiago Moreira"))
                .thenReturn(Optional.empty());

        when(musicaRepository.getMusicas("Hiago Moreira"))
                .thenReturn(Optional.empty());

        when(musicaRepository.getMusicasMaisTocadas("Hiago Moreira", 5))
                .thenReturn(Optional.empty());


        //When : utilização do serviço buscando pelo nome do artista
        Optional<String> biografia = artistaService.getBiografia("Hiago Moreira");
        Optional<List<IMusica>> musicas = artistaService.getMusica("Hiago Moreira");
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas("Hiago Moreira");

        //Then : verifica a chamada no artista service
        assertFalse(biografia.isPresent());
        assertFalse(musicas.isPresent());
        assertFalse(musicasMaisTocadas.isPresent());

        verify(artistaRepository, times(1)).getBiografia("Hiago Moreira");
        verify(musicaRepository, times(1)).getMusicas("Hiago Moreira");
        verify(musicaRepository, times(1)).getMusicasMaisTocadas("Hiago Moreira", 5);
    }


    @Test
    public void verificaRetornoBibliografiaTest(){
        //Given: Artista mockado no repositorio
        Artista artistaMocked = new Artista("Hiago Moreira", "Um cara legal !");
        when(artistaRepository.getBiografia("Hiago Moreira"))
                .thenReturn(Optional.ofNullable(artistaMocked.getBiografia()));

        //When : utilização do serviço buscando pelo nome do artista
        Optional<String> biografia = artistaService.getBiografia("Hiago Moreira");

        //Then : verifica a chamada no artista service
        assertTrue(biografia.isPresent());
        assertEquals("Um cara legal !", biografia.get());
        verify(artistaRepository, times(1)).getBiografia("Hiago Moreira");
    }


    @Test
    public void verificaRetornoMusicas(){
        //Given: Artista mockado no repositorio
        when(musicaRepository.getMusicas("Hiago Moreira"))
                .thenReturn(Optional.ofNullable(null));

        //When : utilização do serviço buscando pelo nome do artista
        Optional<String> biografia = artistaService.getBiografia("Hiago Moreira");

        //Then : verifica a chamada no artista service
        assertFalse(biografia.isPresent());
        verify(artistaRepository, times(1)).getBiografia("Hiago Moreira");
    }

    @Test
    public void verificaRetornoMusicasMaisTocadas(){
        //Given: Artista mockado no repositorio
        when(musicaRepository.getMusicasMaisTocadas("Hiago Moreira", 5))
                .thenReturn(Optional.ofNullable(null));

        //When : utilização do serviço buscando pelo nome do artista
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas("Hiago Moreira");

        //Then : verifica a chamada no artista service
        assertFalse(musicasMaisTocadas.isPresent());
        verify(musicaRepository, times(1)).getMusicasMaisTocadas("Hiago Moreira", 5);
    }

    @Test
    public void verificaAtualizacaoReproducaoTest(){
        //Given: Musica mockada
        IMusica musicaMocked = mock(IMusica.class);

        //When : utilização do serviço buscando pelo nome do artista
        artistaService.atualizarEstatisticasReproducao(musicaMocked);

        //Then : verifica a chamada no artista service
        verify(musicaRepository, times(1)).atualizarEstatisticasReproducao(musicaMocked);
    }

}
