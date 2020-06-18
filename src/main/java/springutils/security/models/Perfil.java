package springutils.security.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PERFIL")
public class Perfil {

	@Id
	@GenericGenerator(name = "perfilSequenceGenerator", parameters = {@Parameter(name = "sequence_name", value = "PERFIL_SEQUENCE")}, strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
	@GeneratedValue(generator = "perfilSequenceGenerator")
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME", unique = true)
	private String nome;

	@Column(name = "DESCRICAO")
	private String descricao;

	@JoinTable(name="PERFIL_PERMISSAO",
			joinColumns=@JoinColumn(name="PERFIL_ID", referencedColumnName="ID", foreignKey = @ForeignKey(name = "PERFIL_PERMISSAO_FK")),
			inverseJoinColumns={@JoinColumn(name="PERMISSAO_ID", referencedColumnName="ID", foreignKey = @ForeignKey(name = "PERMISSAO_PERFIL_FK"))})
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<Permissao> permissoes = new ArrayList<>();

	@Column(name = "DATA_CRIACAO")
	private Date dataCriacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
