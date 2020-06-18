package springutils.security.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "USUARIO_APPLICATION")
public class UsuarioApplication {

	@Id
	@GenericGenerator(name = "usuarioApplicationSequenceGenerator", parameters = {@Parameter(name = "sequence_name", value = "USUARIO_APPLICATION_SEQUENCE"), @Parameter(name = "initial_value", value = "6")}, strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
	@GeneratedValue(generator = "usuarioApplicationSequenceGenerator")
	@Column(name = "ID")
	private Long id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "USERNAME", unique = true)
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "NOME", unique = true)
	private String nome;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private StatusUsuario status;

	@Column(name = "TIPO_USUARIO")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	@JoinColumn(name = "PERFIL_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "USUARIO_APPLICATION_PERFIL_FK"))
	@ManyToOne(fetch = FetchType.EAGER)
	private Perfil perfil;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public StatusUsuario getStatus() {
		return status;
	}

	public void setStatus(StatusUsuario status) {
		this.status = status;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UsuarioApplication that = (UsuarioApplication) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}