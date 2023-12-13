package atjava.api.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import atjava.api.Model.Pessoa;
import atjava.api.Model.PostBody;



@ExtendWith(MockitoExtension.class)
class MockServiceTest {

    @InjectMocks
    private MockService mockService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        mockService = new MockService();
    }

    @Test
    void testolharPessoaPorNome() {
        List<Pessoa> pessoas = mockService.olharPessoa("Nina", null);
        assertEquals(1, pessoas.size());
        assertEquals("Nina", pessoas.get(0).getNome());
    }

    @Test
    void testolharPessoaPorIdade() {
        List<Pessoa> pessoas = mockService.olharPessoa(null, 8);
        assertEquals(2, pessoas.size());
        assertEquals(8, pessoas.get(0).getNiver());
    }

    @Test
    void testAdicionarPessoa() {
        Pessoa novaPessoa = new Pessoa("Nova Pessoa", 4,new String[]{"cola", "bic"},null);
        
        assertDoesNotThrow(() -> mockService.adicionaPessoa(novaPessoa));
        List<Pessoa> pessoas = mockService.olharPessoa(null, null);
        assertTrue(pessoas.contains(novaPessoa));
    }

    @Test
    void testAdicionarPessoaSemNome() {
        Pessoa pessoaComErro = new Pessoa(null, 4,new String[]{"cola", "bic"}, null);
       
        InvalidAttributesException  exception = assertThrows(InvalidAttributesException .class, () -> mockService.adicionaPessoa(pessoaComErro));
        assertEquals("Digite um nome", exception.getMessage());
    }

    @Test
    void testAdicionarPessoaSemNiver() {
        Pessoa pessoaComErro = new Pessoa("Nova Pessoa", null,new String[]{"cola", "bic"},null);
        InvalidAttributesException  exception = assertThrows(InvalidAttributesException .class, () -> mockService.adicionaPessoa(pessoaComErro));
        assertEquals("Digite seu niver", exception.getMessage());
    }

    @Test
    void testAtualizarFrases() {
        PostBody pessoa = new PostBody(null, 8,new String[]{"cola", "bic"});
        List<Pessoa> pessoasAtualizadas = mockService.atualizarFrase(pessoa);
        assertEquals(2, pessoasAtualizadas.size());
        assertTrue(pessoasAtualizadas.stream().allMatch(p -> p.getFrase() != null));
    }

    @Test
    void testConsultarFraseDaSorteComSucesso() {
        String Frase = mockService.procurarFrase();
        assertEquals("delectus aut autem", Frase);
    }

}