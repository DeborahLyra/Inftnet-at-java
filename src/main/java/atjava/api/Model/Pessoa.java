package atjava.api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pessoa {
	private String nome;
	private Integer niver;
	private String[] estojo; 

	private String frase;
	
}
