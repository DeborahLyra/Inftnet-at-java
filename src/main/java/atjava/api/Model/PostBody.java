package atjava.api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBody {
	private String nome;
	private int niver;
	private String[] estojo; 
}
