package querybuildbyannotation;


import org.springframework.stereotype.Service;
import querybuildbyannotation.annotations.Lista;
import querybuildbyannotation.annotations.SubQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class JpaService {
/*
HOW TO UUSE
listar(PaginacaoVO paginacaoVO, Class<T> classe) {
    final StringBuffer where = new StringBuffer();
    Map<String, Object> parametros = new HashMap<>();
    where.append("   WHERE 1 = 1 ");
    where.append("   ORDER BY cliente.id DESC");
    List<T> clients = jpaService.executeCustomQuery("cliente", "Cliente", classe, where.toString(), parametros, paginacaoVO);

 */
    @PersistenceContext
    private EntityManager entityManager;

    public JpaService() {
    }

    public Long executeCustomCount(String variavel, String tabela, String where, Map<String, Object> parametros) {
        QueryBuilder queryBuilder = QueryBuilder.createCount(variavel, tabela, where);

        TypedQuery<Long> query = this.entityManager.createQuery(queryBuilder.getSelect(), Long.class);

        if (parametros != null) {
            for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
                query.setParameter(parametro.getKey(), parametro.getValue());
            }
        }

        return query.getSingleResult();
    }

    public <T> List<T> executeCustomQuery(String variavel, String tabela, Class<T> classe, String where, Map<String, Object> parametros, PaginacaoVO paginacaoVO) {
        QueryBuilder queryBuilder = QueryBuilder.create(variavel, tabela, classe, where);

        TypedQuery<T> query = this.entityManager.createQuery(queryBuilder.getSelect(), classe);

        if (parametros != null) {
            for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
                query.setParameter(parametro.getKey(), parametro.getValue());
            }
        }

        List<T> results;
        if (paginacaoVO != null) {
            results = query.setFirstResult(paginacaoVO.getLimite() * paginacaoVO.getPagina())
                    .setMaxResults(paginacaoVO.getLimite())
                    .getResultList();
        } else {
            results = query.getResultList();
        }

        String nomeTabela = tabela.substring(0, 1).toLowerCase() + tabela.substring(1);
        this.insertSubQuerys(queryBuilder.getListas(), results, nomeTabela);
        return results;
    }

    public <T> T executeCustomNativeQuery(String variavel, String tabela, Class<T> classe, Integer limiteResultados) {
        QueryBuilder queryBuilder = QueryBuilder.createNative(variavel, tabela, classe);

        Query query = this.entityManager.createNativeQuery(queryBuilder.getSelect());

        if (limiteResultados != null) {
            query.setMaxResults(limiteResultados);
        }

        Object[] valores = (Object[]) query.getSingleResult();

        T t = null;
        try {
            t = classe.newInstance();
            for (int i = 0; i < valores.length; i++) {
                Field field = queryBuilder.getListas().get(i);
                SubQuery annotation = field.getAnnotation(SubQuery.class);
                String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                if (annotation.parseAtivo()) {
                    Class<?> declaringClass = field.getType();
                    Method declaredMethod = t.getClass().getDeclaredMethod("set" + name, declaringClass);
                    Object o = declaringClass.getConstructor(String.class).newInstance(valores[i].toString());
                    declaredMethod.invoke(t, o);
                } else {
                    Method declaredMethod = t.getClass().getDeclaredMethod("set" + name, valores[i].getClass());
                    declaredMethod.invoke(t, valores[i]);
                }


            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return t;
    }

    private <T> void insertSubQuerys(List<Field> fields, List<T> despesas, String nomeTabela) {
        for (T despesa : despesas) {
            for (Field field : fields) {
                String subQuery = QueryBuilder.builderSubQuery(field, nomeTabela);
                try {
                    Field id = despesa.getClass().getDeclaredField("id");
                    id.setAccessible(true);
                    subQuery += ((Long) id.get(despesa));
                    Lista annotation = field.getAnnotation(Lista.class);
                    List<?> objects = this.buscaSubQuery(annotation.classe(), subQuery);
                    String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method declaredMethod = despesa.getClass().getDeclaredMethod("set" + name, List.class);
                    declaredMethod.invoke(despesa, objects);

                } catch (NoSuchFieldException e) {
                    System.out.println("ta fazendo merda né meu amigo");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private <T> List<T> buscaSubQuery(Class<T> classe, String query) {
        TypedQuery<T> subQuery = this.entityManager.createQuery(query, classe);
        return subQuery.getResultList();
    }
}
