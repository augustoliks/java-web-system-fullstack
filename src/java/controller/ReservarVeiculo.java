/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import api.model.Cliente;
import api.model.Reserva;
import api.model.Veiculo;
import api.servico.ReservarVeiculosCaracacteristicas;
import core.servico.ReservarVeiculoServico;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import org.joda.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author visita
 */
@WebServlet(name = "ReservarVeiculo", urlPatterns = {"/ReservarVeiculo"})
public class ReservarVeiculo extends HttpServlet {

    ReservarVeiculosCaracacteristicas reservarVeiculosImpl;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        PrintWriter out = response.getWriter();
        String Rg = null;
        // PEGANDO RG DO CLIENTE PELA SESSION        
        HttpSession session = request.getSession(false);
        
        if(session != null && session.getAttribute("Rg") != null){
            Rg = String.valueOf(session.getAttribute("Rg"));
            
            reservarVeiculosImpl    = new ReservarVeiculoServico();
            Reserva reserva         = new Reserva();

            DateFormat f            = DateFormat.getDateInstance();

            String dataHoraInicio   = request.getParameter("date_ini");
            String dataHoraFim      = request.getParameter("date_fim");

            int idVeiculo           = Integer.valueOf(request.getParameter("id_veiculo"));
            int idCliente           = Integer.valueOf(request.getParameter("id_cliente"));

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

            DateTime dataHoraInicoFormatada = formatter.parseDateTime(dataHoraInicio);
            DateTime dataHoraFimFormatada = formatter.parseDateTime(dataHoraFim);

            reserva.setDataHoraInicio(dataHoraInicoFormatada);
            reserva.setDataHoraTermino(dataHoraFimFormatada);

            reserva.setValorPrevisto(0);

            reserva.setCliente(new Cliente());
            reserva.setVeiculo(new Veiculo());

            reserva.getCliente().setId(idCliente);
            reserva.getVeiculo().setId(idVeiculo);

            boolean statusCadastro = reservarVeiculosImpl.reservar(reserva);
            
        }else{
            out.print("Voce deve estar logado para reservar um veiculo!");
            
            request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
            
            out.close();
            System.out.println("rg da session ============ " + Rg);
        }
        /*
        if (statusCadastro){
            request.setAttribute("statusCadastro", statusCadastro);
        }
        else{
            request.setAttribute("statusCadastro", statusCadastro);
            System.out.println("ERRO DE CADASTRO");
        }
         */
    }

}
