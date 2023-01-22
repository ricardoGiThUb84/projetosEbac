package edu.ebac.projeto.dao;

import edu.ebac.projeto.dominio.Cliente;

import java.util.Collection;

public interface IClienteDAO {

    public Boolean cadastrar(Cliente cliente);
    public void excluir(Long cpf);
    public boolean alterar(Cliente cliente);

    public Cliente consultar(Long cpf);

    public Collection<Cliente> buscartodos();


}
