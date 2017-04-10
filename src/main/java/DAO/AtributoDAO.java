/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.pcmakersa.modelagemcomponente.Modelos.Atributo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author devops
 */
public class AtributoDAO {
    
    
     //Método para cadastrar um Atributo  no banco de dados
    public void cadastrarAtributo(Atributo atributo) {

        EntityManager manager = new JPAUtil().getEntityManager();
        try {
            manager.getTransaction().begin(); // Inicia uma transação
            if (atributo.getValor() == null) {
                manager.persist(atributo);
            } else {
                manager.merge(atributo); // Carrega a entitade Atributo e o torna MANAGED
            }
            manager.flush(); // Força uma sincronia com o banco de dados
            manager.getTransaction().commit(); // Comita uma transação. 
            System.out.println(" Novo Tipo Atributo inserido com sucesso");
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();// Executa um rollback em caso de erros
            System.out.println("Erro ao inserir um Atributo novo: "+ex);
        } finally {
            manager.close(); //Encerra uma transação
        }

    }
    
    //Metodo para consultar todos os Atributos  existentes
    public List<Atributo> consultarAtributo() {

        // Instancia um objeto EntityManager para utilizar operações SQL
        EntityManager manager = new JPAUtil().getEntityManager();
        List<Atributo> litaAtributo = new ArrayList<>();
        TypedQuery<Atributo> query = manager.createQuery("select u from atributo u", Atributo.class);
        litaAtributo = query.getResultList();
        return litaAtributo;
    }
    
    //Metodo para deletar um Atributo banco
    public void deletarAtributo(Atributo atributo) {

        EntityManager manager = new JPAUtil().getEntityManager(); //Inicia um Entity Manager      
        manager.getTransaction().begin(); //Inicia uma transação

        try {
            atributo = manager.find(Atributo.class, atributo.getId()); // Resgata um ATRIBUTO   através da primary key
            manager.remove(atributo); //Exclui o atributo do Banco de dados.
            manager.getTransaction().commit(); //Comita a transação 
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao deletar Atributo: "+ex);
        }
        manager.close(); //Fecha a transação

    }
    
    //Metodo para alterar um Atributo
    public void alterarAtributo(Atributo atributo) {

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        try{
            if(atributo.getValor() != null){
            manager.merge(atributo);
            manager.getTransaction().commit();
            System.err.println("Tipo Atributo  alterado com sucesso");
            }
        } catch(HibernateException ex){
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao alterar um atributo: "+ex);
        } finally{
            manager.close();
        }

    }
    
    
    
}
