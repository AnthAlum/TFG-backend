package backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ListaClientes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")


public class ListaClientes   {
  @JsonProperty("clientes")
  @Valid
  private List<Cliente> clientes = null;

  public ListaClientes clientes(List<Cliente> clientes) {
    this.clientes = clientes;
    return this;
  }

  public ListaClientes addClientesItem(Cliente clientesItem) {
    if (this.clientes == null) {
      this.clientes = new ArrayList<Cliente>();
    }
    this.clientes.add(clientesItem);
    return this;
  }

  /**
   * Get clientes
   * @return clientes
   **/
  @Schema(description = "")
      @Valid
    public List<Cliente> getClientes() {
    return clientes;
  }

  public void setClientes(List<Cliente> clientes) {
    this.clientes = clientes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListaClientes listaClientes = (ListaClientes) o;
    return Objects.equals(this.clientes, listaClientes.clientes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaClientes {\n");
    
    sb.append("    clientes: ").append(toIndentedString(clientes)).append("\n");
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
