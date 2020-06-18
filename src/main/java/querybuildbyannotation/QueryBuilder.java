package querybuildbyannotation;

import querybuildbyannotation.annotations.Classe;
import querybuildbyannotation.annotations.Lista;
import querybuildbyannotation.annotations.SubQuery;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private String select;
    private List<Field> listas;

    private QueryBuilder() {
    }

    public String getSelect() {
        return select;
    }

    public List<Field> getListas() {
        return listas;
    }

    public static QueryBuilder createCount(String variavel, String tabela, String where) {
        QueryBuilder queryBuilder = new QueryBuilder();
        StringBuilder builder = new StringBuilder();

        builder.append("SELECT ")
                .append(" COUNT( ")
                .append(variavel)
                .append(" ) ")
                .append(" FROM ")
                .append(tabela)
                .append(" ")
                .append(variavel);

        if (where != null) {
            builder.append(" ")
                    .append(where);
        }

        queryBuilder.select = builder.toString();

        return queryBuilder;
    }

    public static QueryBuilder create(String variavel, String tabela, Class classe, String where) {
        QueryBuilder queryBuilder = new QueryBuilder();
        Field[] fields = classe.getDeclaredFields();
        StringBuilder builder = new StringBuilder();

        List<Field> objetos = new ArrayList<>();
        queryBuilder.listas = new ArrayList<>();

        builder.append("SELECT ");
        builder.append("new ");
        builder.append(classe.getName());
        builder.append("(");

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Lista.class)) {
                queryBuilder.listas.add(fields[i]);
                continue;
            } else if (fields[i].isAnnotationPresent(Transient.class)) {
                continue;
            } else if (fields[i].isAnnotationPresent(SubQuery.class)) {
                SubQuery annotation = fields[i].getAnnotation(SubQuery.class);
                builder.append("(");
                builder.append(annotation.query());
                builder.append(")");
            } else if (fields[i].isAnnotationPresent(Classe.class)) {
                objetos.add(fields[i]);
                builder.append(fields[i].getName());
            } else {
                builder.append(variavel);
                builder.append(".");
                builder.append(fields[i].getName());
            }
            if (fields.length != i + 1) {
                builder.append(", ");
            }
        }

        if (builder.toString().endsWith(", ")) {
            builder.delete(builder.length() - 2, builder.length());
        }

        builder.append(") ");
        builder.append(" FROM ");
        builder.append(tabela);
        builder.append(" ");
        builder.append(variavel);

        for (Field objeto : objetos) {
            builder.append(" LEFT JOIN ");
            builder.append(variavel);
            builder.append(".");
            builder.append(objeto.getName());
            builder.append(" ");
            builder.append(objeto.getName());
        }

        if (where != null) {
            builder.append(" ");
            builder.append(where);
        }

        queryBuilder.select = builder.toString();

        return queryBuilder;
    }

    public static QueryBuilder createNative(String variavel, String tabela, Class classe) {
        QueryBuilder queryBuilder = new QueryBuilder();
        Field[] fields = classe.getDeclaredFields();
        StringBuilder builder = new StringBuilder();

        queryBuilder.listas = new ArrayList<>();

        builder.append("SELECT ");

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Transient.class)) {
                continue;
            } else if (fields[i].isAnnotationPresent(SubQuery.class)) {
                SubQuery annotation = fields[i].getAnnotation(SubQuery.class);
                builder.append("(");
                builder.append(annotation.query());
                builder.append(") as \"");
                builder.append(fields[i].getName());
                builder.append("\"");
                queryBuilder.listas.add(fields[i]);
            } else {
                builder.append(variavel);
                builder.append(".");
                builder.append(fields[i].getName());
            }
            if (fields.length != i + 1) {
                builder.append(", ");
            }
        }

        if (builder.toString().endsWith(", ")) {
            builder.delete(builder.length() - 2, builder.length());
        }
//      Comentado pos só tem subquery, e subquery nao precisa disso
//		builder.append(" FROM financial.");
//		builder.append(tabela);
//		builder.append(" ");
//		builder.append(variavel);

        queryBuilder.select = builder.toString();

        return queryBuilder;
    }

    public static String builderSubQuery(Field field, String tabela) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append(field.getName());
        builder.append(" FROM ");
        Lista annotation = field.getAnnotation(Lista.class);
        builder.append(annotation.classe().getSimpleName());
        builder.append(" ");
        builder.append(field.getName());
        builder.append(" WHERE ");
        builder.append(field.getName());
        builder.append(".");
        builder.append(tabela);
        builder.append(".id = ");

        return builder.toString();
    }



}
/*
@SubQuery(query = "SELECT COUNT(tabela.id) FROM Tabela tabela " +
			"WHERE tabela.status = 'AGUARDANDO_APROVACAO' " +
			"OR tabela.status = 'AGUARDANDO_APROVACAO_COISA' ",
			optional = "AND tabela.cliente_id = :clienteID " +
					"AND tabela.responsavel_criacao_id = :responsavelID ",
			parseAtivo = true)
	private BigDecimal quantidadetabela = BigDecimal.ZERO;

 */