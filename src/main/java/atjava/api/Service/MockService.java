package atjava.api.Service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import atjava.api.Model.FraseLatim;
import atjava.api.Model.Pessoa;
import atjava.api.Model.PostBody;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MockService {
	private List<Pessoa> pessoaLista; 
	
	public MockService()
	{
		pessoaLista = new ArrayList<>();
		String estojo[]  = {"corretivo", "lapis-de-cor"};
		pessoaLista.add(new Pessoa("Nina",8, estojo,null));
		pessoaLista.add(new Pessoa("Maria",8, estojo, null));
	}
	
	public List<Pessoa> olharPessoa(String nome, Integer niver)
	{
		if(nome != null) {
			return pessoaLista.stream().filter(pessoa -> pessoa.getNome().equals(nome)).toList();
		}
		if(niver != null) {
			return pessoaLista.stream().filter(pessoa -> pessoa.getNiver().equals(niver)).toList();
		}
		return pessoaLista;
	}
	
	
	public Pessoa adicionaPessoa (Pessoa pessoa) throws InvalidAttributesException 
	{
		if (pessoa.getNome() == null) {
			throw new InvalidAttributesException ("Digite um nome");
		}
		if (pessoa.getNiver() == null) {
			throw new InvalidAttributesException ("Digite seu niver");
		}
		
		pessoaLista.add(pessoa);
		
		return pessoa;
	}
	
	public String procurarFrase()
	{
		RestTemplate restTemplate = new RestTemplate();
       
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos/1", String.class);
            log.info("Status da chamada à API externa: {}", response.getStatusCode());
             String body = response.getBody();
             FraseLatim fraseLatim  = new Gson().fromJson(body, FraseLatim.class);
            
            return fraseLatim.getTitle();
        } catch (Exception e) {
        	log.error("Erro na chamada à API externa: {}", e.getMessage());
            throw new RuntimeException("Erro na chamada à API externa");
        }
        
	}
	
	public List<Pessoa> atualizarFrase(PostBody postBody) {
		List<Pessoa> pessoas = olharPessoa(postBody.getNome(), postBody.getNiver());
		pessoas.forEach(p -> p.setFrase(procurarFrase()));
		return pessoas;
	}
}
