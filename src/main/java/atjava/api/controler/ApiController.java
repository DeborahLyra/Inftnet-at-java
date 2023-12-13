package atjava.api.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import atjava.api.Model.Pessoa;
import atjava.api.Model.PostBody;
import atjava.api.Service.MockService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private MockService mockService;
	
	@GetMapping()
	
	public ResponseEntity<List<Pessoa>> buscar(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer niver)
	{
		return new ResponseEntity<>(mockService.olharPessoa(nome, niver), HttpStatus.OK);
	}
	  
	@PostMapping
	public ResponseEntity<String> post(@RequestBody Pessoa body) {

		try {
			mockService.adicionaPessoa(body);
			return new ResponseEntity<>("A Pessoa foi adicionada!", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping
	public ResponseEntity<List<Pessoa>> put(
			@RequestBody
			PostBody post) {
		List<Pessoa> pessoas = mockService.atualizarFrase(post);
		return new ResponseEntity<>(pessoas, HttpStatus.OK);
	}
	
	@DeleteMapping
	
	public ResponseEntity<String> delete(
			@RequestParam(required = false) String nome)
	{
		return new ResponseEntity<>(nome, HttpStatus.OK);
	}
}
