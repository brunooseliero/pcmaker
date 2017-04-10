/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.pcmakersa.modelagemcomponente.Modelos.TipoAtributo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author devops
 */
public class TipoAtributoDAO {
    
    //Método para cadastrar um Tipo atributo  no banco de dados
    public void cadastrarTipoAtributo(TipoAtributo TipoAtributo) {

        EntityManager manager = new JPAUtil().getEntityManager();
        try {
            manager.getTransaction().begin(); // Inicia uma transação
            if (TipoAtributo.getNome() == null) {
                manager.persist(TipoAtributo);
            } else {
                manager.merge(TipoAtributo); // Carrega a entitade TipoAtributo e o torna MANAGED
            }
            manager.flush(); // Força uma sincronia com o banco de dados
            manager.getTransaction().commit(); // Comita uma transação. 
            System.out.println(" Novo Tipo Atributo inserido com sucesso");
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();// Executa um rollback em caso de erros
            System.out.println("Erro ao inserir um tipo atriuto novo: "+ex);
        } finally {
            manager.close(); //Encerra uma transação
        }

    }
    
    //Metodo para consultar todos os tipos de atributos existentes
    public List<TipoAtributo> consultarTipoAtributo() {

        // Instancia um objeto EntityManager para utilizar operações SQL
        EntityManager manager = new JPAUtil().getEntityManager();
        List<TipoAtributo> litaTipoAtributo = new ArrayList<>();
        TypedQuery<TipoAtributo> query = manager.createQuery("select u from tipo_atributo u", TipoAtributo.class);
        litaTipoAtributo = query.getResultList();
        return litaTipoAtributo;
    }
    
    //Metodo para deletar um Tipo atributo do banco
    public void deletarTipoAtributo(TipoAtributo TipoAtributo) {

        EntityManager manager = new JPAUtil().getEntityManager(); //Inicia um Entity Manager      
        manager.getTransaction().begin(); //Inicia uma transação

        try {
            TipoAtributo = manager.find(TipoAtributo.class, TipoAtributo.getId()); // Resgata um Tipo Atributo através da primary key
            manager.remove(TipoAtributo); //Exclui o tipoAtributo do Banco de dados.
            manager.getTransaction().commit(); //Comita a transação 
        } catch (Exception ex) {
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao deletar tipoAtributo: "+ex);
        }
        manager.close(); //Fecha a transação

    }
    
    //Metodo 
    public void alterarTipoAtributo(TipoAtributo TipoAtributo) {

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        try{
            if(TipoAtributo.getNome() != null){
            manager.merge(TipoAtributo);
            manager.getTransaction().commit();
            System.err.println("Tipo atributo  alterado com sucesso");
            }
        } catch(HibernateException ex){
            ex.getMessage();
            manager.getTransaction().rollback();
            System.err.println("Erro ao alterar um tipo atributo: "+ex);
        } finally{
            manager.close();
        }

    }
    
    // teste no tipo atributo para ver se funciona todas as alteracoes 
    
    
}
