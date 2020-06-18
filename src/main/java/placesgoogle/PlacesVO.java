package placesgoogle;


/**
 * Classe V.O. utilizada para lugares próximos de um imóvel
 * 
 * @author Richard Martins
 * @since 28/09/2016 10:56
 */
public class PlacesVO {
	
	private String idPlace;
	private String latitude;
	private String longitude;
	private String icone;
	private String nome;
	private String endereco;
	private String tipo;
	private Double distanciaDoEndereco;
	
	public PlacesVO() {
	}
	
	public String getIdPlace() {
		return idPlace;
	}
	
	public void setIdPlace(String idPlace) {
		this.idPlace = idPlace;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getIcone() {
		return icone;
	}
	
	public void setIcone(String icone) {
		this.icone = icone;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getDistanciaDoEndereco() {
		return distanciaDoEndereco;
	}

	public void setDistanciaDoEndereco(Double distanciaDoEndereco) {
		this.distanciaDoEndereco = distanciaDoEndereco;
	}
}
