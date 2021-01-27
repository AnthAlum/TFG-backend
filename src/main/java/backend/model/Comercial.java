package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Esquema que representa un comercial
 */
@Schema(description = "Esquema que representa un comercial")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")


public class Comercial   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("idRol")
  private Integer idRol = null;

  @JsonProperty("cif")
  private String cif = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("telefono")
  private String telefono = null;

  public Comercial id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "12345", description = "")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Comercial nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
   **/
  @Schema(example = "Juan Antonio Gonzalez Carrasco1", description = "")
  
    public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Comercial idRol(Integer idRol) {
    this.idRol = idRol;
    return this;
  }

  /**
   * Get idRol
   * @return idRol
   **/
  @Schema(example = "1", description = "")
  
    public Integer getIdRol() {
    return idRol;
  }

  public void setIdRol(Integer idRol) {
    this.idRol = idRol;
  }

  public Comercial cif(String cif) {
    this.cif = cif;
    return this;
  }

  /**
   * Get cif
   * @return cif
   **/
  @Schema(example = "12345678H", description = "")
  
    public String getCif() {
    return cif;
  }

  public void setCif(String cif) {
    this.cif = cif;
  }

  public Comercial email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "juangoncarrasco@example.com", description = "")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Comercial telefono(String telefono) {
    this.telefono = telefono;
    return this;
  }

  /**
   * Get telefono
   * @return telefono
   **/
  @Schema(example = "123456789", description = "")
  
    public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comercial comercial = (Comercial) o;
    return Objects.equals(this.id, comercial.id) &&
        Objects.equals(this.nombre, comercial.nombre) &&
        Objects.equals(this.idRol, comercial.idRol) &&
        Objects.equals(this.cif, comercial.cif) &&
        Objects.equals(this.email, comercial.email) &&
        Objects.equals(this.telefono, comercial.telefono);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, idRol, cif, email, telefono);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comercial {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    idRol: ").append(toIndentedString(idRol)).append("\n");
    sb.append("    cif: ").append(toIndentedString(cif)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
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
