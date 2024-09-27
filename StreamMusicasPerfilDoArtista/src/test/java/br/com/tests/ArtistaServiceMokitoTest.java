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
    public void verificarRetornoSeNaoExistirArtista() throws Exception {
        //Given
        when(artistaRepository.getBiografia(artistaMocked.getNome()))
            .thenReturn(Optional.empty());

        when(musicaRepository.getMusicas(artistaMocked.getNome()))
            .thenReturn(Optional.empty());

        when(musicaRepository.getMusicasMaisTocadas(artistaMocked.getNome(), 5))
            .thenReturn(Optional.empty());

        //When
        Optional<String> biografia = artistaService.getBiografia(artistaMocked.getNome());
        Optional<List<IMusica>> musicas = artistaService.getMusicas(artistaMocked.getNome());
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas(artistaMocked.getNome());


        //Then
        assertFalse(biografia.isPresent(), "Artista está presente");
        assertFalse(musicas.isPresent(), "Musica está presente");
        assertFalse(musicasMaisTocadas.isPresent(), "Musica false está presente");

        verify(artistaRepository, times(1)).getBiografia(artistaMocked.getNome());
        verify(musicaRepository, times(1)).getMusicas(artistaMocked.getNome());
        verify(musicaRepository, times(1)).getMusicasMaisTocadas(artistaMocked.getNome(), 5);
    }

    @Test
    public void verificarRetornoSeOcorrerErros() throws Exception {
        //When
        doThrow(new Exception()).when(artistaRepository).getBiografia(artistaMocked.getNome());
        doThrow(new Exception()).when(musicaRepository).getMusicas(artistaMocked.getNome());
        doThrow(new Exception()).when(musicaRepository).getMusicasMaisTocadas(artistaMocked.getNome(), 5);

        //Given
        Optional<String> biografia = artistaService.getBiografia(artistaMocked.getNome());
        Optional<List<IMusica>> musicas = artistaService.getMusicas(artistaMocked.getNome());
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas(artistaMocked.getNome());

        //Then
        assertFalse(biografia.isPresent(), "Artista está presente");
        assertFalse(musicas.isPresent(), "Musica está presente");
        assertFalse(musicasMaisTocadas.isPresent(), "Musica false está presente");

        verify(artistaRepository, times(1)).getBiografia(artistaMocked.getNome());
        verify(musicaRepository, times(1)).getMusicas(artistaMocked.getNome());
        verify(musicaRepository, times(1)).getMusicasMaisTocadas(artistaMocked.getNome(), 5);
    }


    @Test
    public void verificarRetornoBibliografiaTest() throws Exception {
        //Given
        when(artistaRepository.getBiografia(artistaMocked.getNome()))
                .thenReturn(Optional.ofNullable(artistaMocked.getBiografia()));

        //When
        Optional<String> biografia = artistaService.getBiografia(artistaMocked.getNome());

        //Then
        assertTrue(biografia.isPresent());
        assertEquals(artistaMocked.getBiografia(), biografia.get());
        verify(artistaRepository, times(1)).getBiografia(artistaMocked.getNome());
    }


    @Test
    public void verificarRetornoMusicas() throws Exception {
        //Given
        List<IMusica> musicaRetono = new ArrayList<IMusica>();
        musicaRetono.add(musicaMocked);
        when(musicaRepository.getMusicas(artistaMocked.getNome()))
                .thenReturn(Optional.of(musicaRetono));

        //When
        Optional<List<IMusica>> listaMusica = artistaService.getMusicas(artistaMocked.getNome());

        //Then
        assertTrue(listaMusica.isPresent());
        verify(musicaRepository, times(1)).getMusicas(artistaMocked.getNome());
    }

    @Test
    public void verificarRetornoMusicasMaisTocadas() throws Exception {
        //Given
        List<IMusica> musicaRetorno = new ArrayList<IMusica>();
        musicaRetorno.add(musicaMocked);
        when(musicaRepository.getMusicasMaisTocadas(artistaMocked.getNome(), 5))
                .thenReturn(Optional.of(musicaRetorno));

        //When
        Optional<List<IMusica>> musicasMaisTocadas = artistaService.getMusicasMaisTocadas(artistaMocked.getNome());

        //Then
        assertTrue(musicasMaisTocadas.isPresent());
        verify(musicaRepository, times(1)).getMusicasMaisTocadas(artistaMocked.getNome(), 5);
    }

    @Test
    public void verificarAtualizacaoReproducaoArtistaInexistenteTest() throws Exception {
        //Given
        doThrow(new Exception("Artista " + musicaMocked.getArtista() + " não existe na plataforma"))
            .when(musicaRepository).atualizarEstatisticasReproducao(musicaMocked);

        //When
        Exception exception = assertThrows(Exception.class , () ->{
            artistaService.atualizarEstatisticasReproducao(musicaMocked);
        });

        assertEquals("Artista " + musicaMocked.getArtista() + " não existe na plataforma", exception.getMessage());

        //Then
        verify(musicaRepository, times(1)).atualizarEstatisticasReproducao(musicaMocked);
    }

    @Test
    public void verificarAtualizacaoReproducaoErrorComBancoDadosTest() throws Exception {
        //Given
        doThrow(new Exception("ReproducaoService não está disponível"))
            .when(musicaRepository).atualizarEstatisticasReproducao(musicaMocked);

        //When
        Exception exception = assertThrows(Exception.class , () ->{
            artistaService.atualizarEstatisticasReproducao(musicaMocked);
        });

        assertEquals("ReproducaoService não está disponível", exception.getMessage());

        //Then
        verify(musicaRepository, times(1)).atualizarEstatisticasReproducao(musicaMocked);
    }
}
