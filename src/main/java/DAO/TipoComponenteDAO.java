/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.pcmakersa.modelagemcomponente.Modelos.TipoComponente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author devops
 */
public class TipoComponenteDAO {
    
    //Método para cadastrar um Tipo componente  no banco de dados
    public void cadastrarTipoComponente(TipoComponente TipoComponente) {

        EntityManager manager = new JPAUtil().getEntityManager();
        try {
            manager.getTransaction().begin(); // Inicia uma transação
            if (TipoComponente.getNomeTipo() == null) {
                manager.persist(TipoComponente);
            } else {
                manager.merge(TipoComponente); // Carrega a entitade TipoComponente e o torna MANAGED
            }
            manager.flush(); // Força uma sincronia com o banco de dados
            manager.getTransaction().commit(); // Comita uma transação. 
            System.out.println(" Novo Tipo Componente inserido com sucesso");
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();// Executa um rollback em caso de erros
            System.out.println("Erro ao inserir um tipo Componente novo: "+ex);
        } finally {
            manager.close(); //Encerra uma transação
        }

    }
    
    //Metodo para consultar todos os tipos de componentes  existentes
    public List<TipoComponente> consultarTipoComponente() {

        // Instancia um objeto EntityManager para utilizar operações SQL
        EntityManager manager = new JPAUtil().getEntityManager();
        List<TipoComponente> litaTipoComponente = new ArrayList<>();
        TypedQuery<TipoComponente> query = manager.createQuery("select u from tipo_componente u", TipoComponente.class);
        litaTipoComponente = query.getResultList();
        return litaTipoComponente;
    }
    
    //Metodo para deletar um Tipo atributo do banco
    public void deletarTipoComponente(TipoComponente TipoComponente) {

        EntityManager manager = new JPAUtil().getEntityManager(); //Inicia um Entity Manager      
        manager.getTransaction().begin(); //Inicia uma transação

        try {
            TipoComponente = manager.find(TipoComponente.class, TipoComponente.getId()); // Resgata um Tipo Componente  através da primary key
            manager.remove(TipoComponente); //Exclui o tipoComponente do Banco de dados.
            manager.getTransaction().commit(); //Comita a transação 
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao deletar tipoComponente: "+ex);
        }
        manager.close(); //Fecha a transação

    }
    
    //Metodo para alterar um tipo de um componente
    public void alterarTipoComponente(TipoComponente TipoComponente) {

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        try{
            if(TipoComponente.getNomeTipo() != null){
            manager.merge(TipoComponente);
            manager.getTransaction().commit();
            System.err.println("Tipo Componente  alterado com sucesso");
            }
        } catch(HibernateException ex){
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao alterar um tipo componente: "+ex);
        } finally{
            manager.close();
        }

    }
    
}
