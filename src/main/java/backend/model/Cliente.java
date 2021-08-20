package backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Cliente
 */
@Validated

public class Cliente   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("idComerciante")
  private Long idComerciante = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("telefono")
  private String telefono = null;

  @JsonProperty("empresa")
  private String empresa = null;

  public Cliente id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "67890", description = "")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Cliente nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
   **/
  @Schema(example = "Fernando Carlos Roca Rivas", description = "")
  
    public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Cliente idComerciante(Long idComerciante) {
    this.idComerciante = idComerciante;
    return this;
  }

  /**
   * Get idComerciante
   * @return idComerciante
   **/
  @Schema(description = "")
  
    public Long getIdComerciante() {
    return idComerciante;
  }

  public void setIdComerciante(Long idComerciante) {
    this.idComerciante = idComerciante;
  }

  public Cliente email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "correoexample@example.com", description = "")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Cliente telefono(String telefono) {
    this.telefono = telefono;
    return this;
  }

  /**
   * Get telefono
   * @return telefono
   **/
  @Schema(example = "987654321", description = "")
  
    public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Cliente empresa(String empresa) {
    this.empresa = empresa;
    return this;
  }

  /**
   * Get empresa
   * @return empresa
   **/
  @Schema(example = "Cinco Jotas", description = "")
  
    public String getEmpresa() {
    return empresa;
  }

  public void setEmpresa(String empresa) {
    this.empresa = empresa;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cliente cliente = (Cliente) o;
    return Objects.equals(this.id, cliente.id) &&
        Objects.equals(this.nombre, cliente.nombre) &&
        Objects.equals(this.idComerciante, cliente.idComerciante) &&
        Objects.equals(this.email, cliente.email) &&
        Objects.equals(this.telefono, cliente.telefono) &&
        Objects.equals(this.empresa, cliente.empresa);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, idComerciante, email, telefono, empresa);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cliente {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    idComerciante: ").append(toIndentedString(idComerciante)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    empresa: ").append(toIndentedString(empresa)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
