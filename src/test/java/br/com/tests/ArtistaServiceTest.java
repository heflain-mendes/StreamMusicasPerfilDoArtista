package br.com.tests;

import br.com.services.ArtistaRepository;
import br.com.services.ArtistaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArtistaServiceTest {

    private static ArtistaService artistaService;

    @BeforeAll
    public static void setUp() {
        ArtistaRepository artistaRepository = new ArtistaRepository();
        artistaService = new ArtistaService(artistaRepository);
    }

    @Test
    public void testIfSumsTwoNumbers(){
        assertEquals(2, 2);
    }

}
