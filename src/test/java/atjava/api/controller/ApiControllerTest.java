package atjava.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import atjava.api.Model.Pessoa;
import atjava.api.Model.PostBody;
import atjava.api.Service.MockService;
import atjava.api.controler.ApiController;


@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @InjectMocks
    private ApiController apiController;

    @Mock
    private MockService mockService;

    @BeforeEach
    void setUp() {
  
        reset(mockService);
    }

    @Test
    void testGet() {
        
        List<Pessoa> pessoasMock = new ArrayList<>();
        when(mockService.olharPessoa("Nina", null)).thenReturn(pessoasMock);

     
        ResponseEntity<List<Pessoa>> response = apiController.buscar("Nina", null);

     
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoasMock, response.getBody());

        
        verify(mockService, times(1)).olharPessoa("Nina", null);
    }

    @Test
    void testPost() throws InvalidAttributesException {
       
        Pessoa novaPessoa = new Pessoa("Nova Pessoa", 4,new String[]{"cola", "bic"}, "Lorem ipsum");

        
        ResponseEntity<String> response = apiController.post(novaPessoa);

      
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("A Pessoa foi adicionada!", response.getBody());

        
        verify(mockService, times(1)).adicionaPessoa(novaPessoa);
    }

    @Test
    void testPostWithError() throws InvalidAttributesException {
        
        Pessoa pessoaComErro = new Pessoa("Nova Pessoa", 4,new String[]{"cola", "bic"}, "Lorem ipsum");
        when(mockService.adicionaPessoa(pessoaComErro)).thenThrow(new InvalidAttributesException ("Campo nome é obrigatório"));

       
        ResponseEntity<String> response = apiController.post(pessoaComErro);

       
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Campo nome é obrigatório", response.getBody());

        verify(mockService, times(1)).adicionaPessoa(pessoaComErro);
    }

    @Test
    void testPut() {
        
    	PostBody body = new PostBody("Nova Pessoa", 4,new String[]{"cola", "bic"});
        List<Pessoa> pessoasAtualizadas = new ArrayList<>();
        when(mockService.atualizarFrase(body)).thenReturn(pessoasAtualizadas);

       
        ResponseEntity<List<Pessoa>> response = apiController.put(body);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoasAtualizadas, response.getBody());

       
        verify(mockService, times(1)).atualizarFrase(body);
    }

    @Test
    void testDelete() {
        
        ResponseEntity<String> response = apiController.delete("Nina");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nina", response.getBody());
    }

}