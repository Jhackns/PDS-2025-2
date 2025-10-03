package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.MediaFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IMediaFileRepositoryTest {
    @Autowired
    private IMediaFileRepository mediaFileRepository;

    private static Long fileId;

    @BeforeEach
    public void setUp() {
        MediaFile mf = new MediaFile();
        mf.setFileName("test.txt");
        mf.setFileType("text/plain");
        mf.setContent("Hola Mundo".getBytes(StandardCharsets.UTF_8));
        MediaFile saved = mediaFileRepository.save(mf);
        fileId = saved.getIdFile();
        System.out.println("[MediaFile-Test] Setup: id=" + fileId + ", name=" + saved.getFileName());
    }

    @Test
    @Order(1)
    public void testGuardarMediaFile() {
        MediaFile mf = new MediaFile();
        mf.setFileName("otro.bin");
        mf.setFileType("application/octet-stream");
        mf.setContent(new byte[]{0x01, 0x02, 0x03});
        MediaFile saved = mediaFileRepository.save(mf);
        System.out.println("[MediaFile-Test] Guardado: id=" + saved.getIdFile());
        assertNotNull(saved.getIdFile());
        assertArrayEquals(new byte[]{0x01, 0x02, 0x03}, saved.getContent());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<MediaFile> found = mediaFileRepository.findById(fileId);
        System.out.println("[MediaFile-Test] BuscarPorId: presente=" + found.isPresent());
        assertTrue(found.isPresent());
        assertEquals("test.txt", found.get().getFileName());
        assertEquals("text/plain", found.get().getFileType());
        assertArrayEquals("Hola Mundo".getBytes(StandardCharsets.UTF_8), found.get().getContent());
    }

    @Test
    @Order(3)
    public void testActualizarMediaFile() {
        MediaFile mf = mediaFileRepository.findById(fileId).orElseThrow();
        mf.setFileName("test-updated.txt");
        mf.setContent("Contenido actualizado".getBytes(StandardCharsets.UTF_8));
        MediaFile updated = mediaFileRepository.save(mf);
        System.out.println("[MediaFile-Test] Actualizado: name=" + updated.getFileName());
        assertEquals("test-updated.txt", updated.getFileName());
        assertArrayEquals("Contenido actualizado".getBytes(StandardCharsets.UTF_8), updated.getContent());
    }

    @Test
    @Order(4)
    public void testListarMediaFiles() {
        List<MediaFile> list = mediaFileRepository.findAll();
        System.out.println("[MediaFile-Test] Listar: total=" + list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarMediaFile() {
        mediaFileRepository.deleteById(fileId);
        Optional<MediaFile> deleted = mediaFileRepository.findById(fileId);
        System.out.println("[MediaFile-Test] Eliminar: presente=" + deleted.isPresent());
        assertFalse(deleted.isPresent());
    }
}