package pe.edu.upeu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServicioATest {

    @Test
    public void testSumar(){
        int a=10, b=2;
        ServicioA servicioA=new ServicioAImpl();
        int resultado=servicioA.sumar(a, b);
        Assertions.assertEquals(12,resultado);
    }
}
