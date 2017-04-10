/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.pcmakersa.modelagemcomponente.Modelos.Componente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author devops
 */
public class ComponenteDAO {
    
    //Método para cadastrar um Componente  no banco de dados
    public void cadastrarComponente(Componente componente) {

        EntityManager manager = new JPAUtil().getEntityManager();
        try {
            manager.getTransaction().begin(); // Inicia uma transação
            if (componente.getNome() == null) {
                manager.persist(componente);
            } else {
                manager.merge(componente); // Carrega a entitade Componente e o torna MANAGED
            }
            manager.flush(); // Força uma sincronia com o banco de dados
            manager.getTransaction().commit(); // Comita uma transação. 
            System.out.println(" Novo Componente inserido com sucesso");
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();// Executa um rollback em caso de erros
            System.out.println("Erro ao inserir um Componente novo: "+ex);
        } finally {
            manager.close(); //Encerra uma transação
        }

    }
    
    //Metodo para consultar todos os Componentes  existentes
    public List<Componente> consultarComponente() {

        // Instancia um objeto EntityManager para utilizar operações SQL
        EntityManager manager = new JPAUtil().getEntityManager();
        List<Componente> litaComponente = new ArrayList<>();
        TypedQuery<Componente> query = manager.createQuery("select u from componente u", Componente.class);
        litaComponente = query.getResultList();
        return litaComponente;
    }
    
    //Metodo para deletar um Componente banco
    public void deletarComponente(Componente componente) {

        EntityManager manager = new JPAUtil().getEntityManager(); //Inicia um Entity Manager      
        manager.getTransaction().begin(); //Inicia uma transação

        try {
            componente = manager.find(Componente.class, componente.getId()); // Resgata um componente   através da primary key
            manager.remove(componente); //Exclui o componente do Banco de dados.
            manager.getTransaction().commit(); //Comita a transação 
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao deletar Componente: "+ex);
        }
        manager.close(); //Fecha a transação

    }
    
    //Metodo para alterar um Componente
    public void alterarComponente(Componente componente) {

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        try{
            if(componente.getNome() != null){
            manager.merge(componente);
            manager.getTransaction().commit();
            System.err.println("Tipo Componente  alterado com sucesso");
            }
        } catch(HibernateException ex){
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao alterar um componente: "+ex);
        } finally{
            manager.close();
        }

    }
    
}
