package autocomplete;

import java.util.List;


/**
 * Classe DAO para a entidade AutoComplete
 * 
 * @author Richard Martins
 * @since 18/01/2016 17:00
 */
//@Repository
public class AutoCompleteRepository { //extends AbstractRepository<AutoComplete> {

	/**
	 * Método responsável por pesquisar os autocompletes com base na palavra recebida
	 * 
	 * @param pesquisa - String
	 * @return palavras - List<AutoComplete>
	 */
	public List<AutoComplete> detailedSearch(String pesquisa) {
		List<AutoComplete> palavras = null;
		
		try {
			final StringBuffer jpql = new StringBuffer();
			jpql.append(" SELECT DISTINCT autoComplete");
			jpql.append(" FROM AutoComplete autoComplete");
			jpql.append(" WHERE autoComplete.chave LIKE :pesquisa");
			jpql.append(" ORDER BY autoComplete.numeroRepeticoes DESC");
		
//			final TypedQuery<AutoComplete> query = this.entityManager.createQuery(jpql.toString(), AutoComplete.class);
//
//			query.setParameter("pesquisa", "%"+pesquisa.trim()+"%");
//
//			palavras = query.getResultList();
			
//			Log.getInstance(Thread.currentThread().getStackTrace()).info(Messager.getMessage("autoComplete.notification.encontrado.com.sucesso") + palavras + ".");
		} catch(Exception e) {
//			Log.getInstance(Thread.currentThread().getStackTrace()).error(Messager.getMessage("autoComplete.notification.erro.ao.encontrar.por.palavra") + pesquisa, e);
		}
		
		return palavras;
	}

	public List<String> searchChaves() {
		List<String> palavras = null;

		try {
			final StringBuffer jpql = new StringBuffer();
			jpql.append(" SELECT DISTINCT autoComplete.chave");
			jpql.append(" FROM AutoComplete autoComplete");
			jpql.append(" WHERE autoComplete.chave NOT LIKE :pesquisa");

//			final TypedQuery<String> query = this.entityManager.createQuery(jpql.toString(), String.class);
//
//			query.setParameter("pesquisa", "% %");
//
//			palavras = query.getResultList();
//
//			Log.getInstance(Thread.currentThread().getStackTrace()).info(Messager.getMessage("autoComplete.notification.encontrado.com.sucesso") + ".");
		} catch(Exception e) {
//			Log.getInstance(Thread.currentThread().getStackTrace()).error(Messager.getMessage("autoComplete.notification.erro.ao.encontrar.por.palavra"), e);
		}

		return palavras;
	}

}