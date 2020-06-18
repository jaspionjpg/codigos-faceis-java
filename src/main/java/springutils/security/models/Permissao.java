package springutils.security.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSAO")
public class Permissao {

	@Id
	@GenericGenerator(name = "permissaoSequenceGenerator", parameters = {@Parameter(name = "sequence_name", value = "PERMISSAO_SEQUENCE")}, strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
	@GeneratedValue(generator = "permissaoSequenceGenerator")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ROLE", unique = true)
	private String role;

	@Column(name = "MODULO_SISTEMA")
	private String moduloSistema;

	@Column(name = "VALIDO")
	private Boolean valido = Boolean.TRUE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getValido() {
		return valido;
	}

	public void setValido(Boolean valido) {
		this.valido = valido;
	}
}
