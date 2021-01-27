package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Comercial;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListaComerciales
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")


public class ListaComerciales   {
  @JsonProperty("comerciales")
  @Valid
  private List<Comercial> comerciales = null;

  public ListaComerciales comerciales(List<Comercial> comerciales) {
    this.comerciales = comerciales;
    return this;
  }

  public ListaComerciales addComercialesItem(Comercial comercialesItem) {
    if (this.comerciales == null) {
      this.comerciales = new ArrayList<Comercial>();
    }
    this.comerciales.add(comercialesItem);
    return this;
  }

  /**
   * Get comerciales
   * @return comerciales
   **/
  @Schema(description = "")
      @Valid
    public List<Comercial> getComerciales() {
    return comerciales;
  }

  public void setComerciales(List<Comercial> comerciales) {
    this.comerciales = comerciales;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListaComerciales listaComerciales = (ListaComerciales) o;
    return Objects.equals(this.comerciales, listaComerciales.comerciales);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comerciales);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaComerciales {\n");
    
    sb.append("    comerciales: ").append(toIndentedString(comerciales)).append("\n");
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
