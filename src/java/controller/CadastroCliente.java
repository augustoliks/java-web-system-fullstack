/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import api.model.Cliente;
import api.servico.ClienteServicoCaracacteristicas;
import core.servico.CadastroClienteServico;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author liks
 */
@WebServlet(name = "CadastroCliente", urlPatterns = {"/CadastroCliente"})
public class CadastroCliente extends HttpServlet {        
    
    ClienteServicoCaracacteristicas cadastroClienteImpl;

    public CadastroCliente() throws IOException {
        cadastroClienteImpl = new CadastroClienteServico();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Cliente novoCliente = new Cliente();
                
        ServletContext sc = request.getServletContext();
        String nome = request.getParameter("nome");
        String rg = request.getParameter("rg");
        String cpf = request.getParameter("cpf");
        String endereco = request.getParameter("endereco");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        novoCliente.setCpf(cpf);
        novoCliente.setEmail(email);
        novoCliente.setEndereco(endereco);
        novoCliente.setNome(nome);
        novoCliente.setRg(rg);
        novoCliente.setSenha(senha);
        
        boolean statusCadastro = cadastroClienteImpl.insersao(novoCliente);

        if (statusCadastro){
            request.setAttribute("statusCadastro", statusCadastro);
        }
        else{
            request.setAttribute("statusCadastro", statusCadastro);
            System.out.println("ERRO DE CADASTRO");
        }
        
    }
}
