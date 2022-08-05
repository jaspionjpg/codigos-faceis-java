package autocomplete;


/**
 * Classe V.O. de comportamento
 * 
 * @author Richard Martins Ferreira de Souza
 * @since 04/07/2016 11:37
 */
public class ComportamentoVO {

	private Long numeroRepeticoes = 0l;
	private String comportamento;
	
	private Float similaridade = 0f;
	
	public ComportamentoVO(String comportamento, Long numeroRepeticoes) {
		this.comportamento = comportamento;
		this.numeroRepeticoes = numeroRepeticoes;
	}
	
	public ComportamentoVO(String comportamento, Float similaridade) {
		this.comportamento = comportamento;
		this.similaridade = similaridade;
	}
	
	public Long getNumeroRepeticoes() {
		return numeroRepeticoes;
	}
	
	public void setNumeroRepeticoes(Long numeroRepeticoes) {
		this.numeroRepeticoes = numeroRepeticoes;
	}
	
	public String getComportamento() {
		return comportamento;
	}
	
	public void setComportamento(String comportamento) {
		this.comportamento = comportamento;
	}
	
	public Float getSimilaridade() {
		return similaridade;
	}
	
	public void setSimilaridade(Float similaridade) {
		this.similaridade = similaridade;
	}
	
	@Override
	public String toString() {
		return this.comportamento;
	}
}
