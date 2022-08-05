package autocomplete;

import java.util.Date;


/**
 * Classe persistente da tabela auto_complete.
 * 
 * @author Richard Martins
 * @since 17/03/2016 10:00
 */
//@ApiModel
//@Entity
//@Table(name="auto_complete")
//@NamedQueries({
//	@NamedQuery(name="AutoComplete.findAll", query="SELECT ac FROM AutoComplete ac"),
//	@NamedQuery(name="AutoComplete.findByChave", query="SELECT ac FROM AutoComplete ac WHERE ac.chave LIKE :chave") })
public class AutoComplete { //extends AbstractEntity {
	private static final long serialVersionUID = 1L;
		
//	@Id
//	@Column(name="chave", length=90, nullable=false)
	private String chave;

//	@Column(name="numeroRepeticoes", nullable=false)
	private Long numeroRepeticoes;
	
//	@Column(name="dataCadastro", nullable=false)
//	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	
	public AutoComplete() {
	}
	
	public String getChave() {
		return  chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
	
	public Long getNumeroRepeticoes() {
		return numeroRepeticoes;
	}
	
	public void setNumeroRepeticoes(Long numeroRepeticoes) {
		this.numeroRepeticoes = numeroRepeticoes;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Override
	public String toString() {
		return this.chave;
	}
}
