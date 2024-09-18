package br.com.tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.interfaces.model.IMusica;
import br.com.interfaces.repository.IArtistaRepository;
import br.com.interfaces.repository.IMusicaRepository;
import br.com.model.Artista;
import br.com.model.Musica;
import br.com.services.ArtistaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ArtistaServiceMokitoTest {

    @Mock
    private IArtistaRepository artistaRepository;

    @Mock
    private IMusicaRepository musicaRepository;

    @InjectMocks
    private ArtistaService artistaService;

    private static Artista artistaMocked;
    private static Musica musicaMocked;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        artistaMocked = new Artista("Hiago Moreira", "Um cara legal !");
        musicaMocked = new Musica("Titulo", artistaMocked.getNome(), "Rock", 22 );
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.artistaService = new ArtistaService(artistaRepository, musicaRepository);
    }

    @AfterEach
    public void tearDown() {
        //clean mocks after tests
        Mockito.reset(artistaRepository, musicaRepository);
    }

    @Test
    public void verificaRetornoSeNaoExistirArtista(){
        //Given
        when(artistaRepository.getBiografia("Hiago Moreira"))
                .thenReturn(Optional.empty());

        when(musicaRepository.getMusicas("Hiago Moreira"))
                .thenReturn(Optional.empty());

        when(musicaRepository.getMusicasMaisTocadas("Hiago Moreira", 5))
                .thenReturn(Optional.empty());


        //When
        Optional<String> biografia = artistaService.getBiografia("Hiago Moreira");
        Optional<List<IMusica>> musicas = artistaService.getMusicas("Hiago Moreira");
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas("Hiago Moreira");

        //Then
        assertFalse(biografia.isPresent());
        assertFalse(musicas.isPresent());
        assertFalse(musicasMaisTocadas.isPresent());

        verify(artistaRepository, times(1)).getBiografia("Hiago Moreira");
        verify(musicaRepository, times(1)).getMusicas("Hiago Moreira");
        verify(musicaRepository, times(1)).getMusicasMaisTocadas("Hiago Moreira", 5);
    }


    @Test
    public void verificaRetornoBibliografiaTest(){
        //Given
        when(artistaRepository.getBiografia("Hiago Moreira"))
                .thenReturn(Optional.ofNullable(artistaMocked.getBiografia()));

        //When
        Optional<String> biografia = artistaService.getBiografia("Hiago Moreira");

        //Then
        assertTrue(biografia.isPresent());
        assertEquals("Um cara legal !", biografia.get());
        verify(artistaRepository, times(1)).getBiografia("Hiago Moreira");
    }


    @Test
    public void verificaRetornoMusicas(){
        //Given
        List<IMusica> musicaRetono = new ArrayList<IMusica>();
        musicaRetono.add(musicaMocked);
        when(musicaRepository.getMusicas("Hiago Moreira"))
                .thenReturn(Optional.of(musicaRetono));

        //When
        Optional<List<IMusica>> listaMusica = artistaService.getMusicas("Hiago Moreira");

        //Then
        assertTrue(listaMusica.isPresent());
        verify(musicaRepository, times(1)).getMusicas("Hiago Moreira");
    }

    @Test
    public void verificaRetornoMusicasMaisTocadas(){
        //Given
        List<IMusica> musicaRetorno = new ArrayList<IMusica>();
        musicaRetorno.add(musicaMocked);
        when(musicaRepository.getMusicasMaisTocadas("Hiago Moreira", 5))
                .thenReturn(Optional.of(musicaRetorno));

        //When
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas("Hiago Moreira");

        //Then
        assertTrue(musicasMaisTocadas.isPresent());
        verify(musicaRepository, times(1)).getMusicasMaisTocadas("Hiago Moreira", 5);
    }

    @Test
    public void verificaAtualizacaoReproducaoTest(){
        //When
        artistaService.atualizarEstatisticasReproducao(musicaMocked);

        //Then
        verify(musicaRepository, times(1)).atualizarEstatisticasReproducao(musicaMocked);
    }

}
