/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.pcmakersa.modelagemcomponente.Modelos.Carrinho;
 import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author devops
 */
public class CarrinhoDAO {
    
    
        //Método para cadastrar um Carrinho  no banco de dados
    public void cadastrarCarrinho(Carrinho carrinho) {

        EntityManager manager = new JPAUtil().getEntityManager();
        try {
            manager.getTransaction().begin(); // Inicia uma transação
            if (carrinho.getComponente() == null) {
                manager.persist(carrinho);
            } else {
                manager.merge(carrinho); // Carrega a entitade Carrinho e o torna MANAGED
            }
            manager.flush(); // Força uma sincronia com o banco de dados
            manager.getTransaction().commit(); // Comita uma transação. 
            System.out.println(" Novo Carrinho inserido com sucesso");
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();// Executa um rollback em caso de erros
            System.out.println("Erro ao inserir um Carrinho novo: "+ex);
        } finally {
            manager.close(); //Encerra uma transação
        }

    }
    
    //Metodo para consultar todos os Carrinhos  existentes
    public List<Carrinho> consultarCarrinho() {

        // Instancia um objeto EntityManager para utilizar operações SQL
        EntityManager manager = new JPAUtil().getEntityManager();
        List<Carrinho> litaCarrinho = new ArrayList<>();
        TypedQuery<Carrinho> query = manager.createQuery("select u from carrinho u", Carrinho.class);
        litaCarrinho = query.getResultList();
        return litaCarrinho;
    }
    
    //Metodo para deletar um Carrinho banco
    public void deletarCarrinho(Carrinho carrinho) {

        EntityManager manager = new JPAUtil().getEntityManager(); //Inicia um Entity Manager      
        manager.getTransaction().begin(); //Inicia uma transação

        try {
            carrinho = manager.find(Carrinho.class, carrinho.getId()); // Resgata um carrinho   através da primary key
            manager.remove(carrinho); //Exclui o carrinho do Banco de dados.
            manager.getTransaction().commit(); //Comita a transação 
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao deletar Carrinho: "+ex);
        }
        manager.close(); //Fecha a transação

    }
    
    //Metodo para alterar um Carrinho
    public void alterarCarrinho(Carrinho carrinho) {

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        try{
            if(carrinho.getComponente() != null){
            manager.merge(carrinho);
            manager.getTransaction().commit();
            System.err.println("Tipo Carrinho  alterado com sucesso");
            }
        } catch(HibernateException ex){
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao alterar um carrinho: "+ex);
        } finally{
            manager.close();
        }

    }
    
}
