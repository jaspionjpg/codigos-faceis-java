package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Classe de regras de negócios para a entidade AutoComplete
 * 
 * @author Richard Martins
 * @since 18/01/2016 16:45
 */
public class AutoCompleteBusiness {

	private AutoCompleteRepository autoCompleteRepository;

	/**
	 * Método responsável por pesquisar complementos de palavras pela escrita
	 * 
	 * @param maxResults - Integer
	 * @param pesquisa - String
	 * @return lista de palavras de auto-complete - List<String>
	 */
	public List<String> search(Integer maxResults, String pesquisa) {
		String inicial = pesquisa;
		List<AutoComplete> frases = new ArrayList<AutoComplete>();
		List<String> preferencia3 = new ArrayList<String>();
		List<String> preferencia2 = new ArrayList<String>();
		List<String> preferencia1 = new ArrayList<String>();
		int of = pesquisa.lastIndexOf(" ");
		String valorDePesquisa;		
		
		if(maxResults == null) {
			maxResults = 15;
		}
		maxResults -= 1;

		if(of > 0) {
			valorDePesquisa = pesquisa.toLowerCase().substring(pesquisa.lastIndexOf(" ") , pesquisa.length());
			pesquisa = (pesquisa.toLowerCase().substring(0, of));
		} else {
			valorDePesquisa = " " + pesquisa.toLowerCase();
			pesquisa = pesquisa.replace(pesquisa, "");
			of = 0;
		}
		
		frases = autoCompleteRepository.detailedSearch(valorDePesquisa);
		int a = 0, b = 0, c=0;
		
		for(AutoComplete autoComplete : frases) {
		
			int p = autoComplete.getChave().indexOf(valorDePesquisa.trim()),
			x = autoComplete.getChave().substring(0 , p = p >= 0 ? p : 0).length() >= 0 ? autoComplete.getChave().substring(0, p).trim().lastIndexOf(" ") : 0,
			m = pesquisa.substring(0,of).length() >= 0 ? pesquisa.substring(0, of).lastIndexOf(" ") : 0;
		
			String subFrases = autoComplete.getChave().substring(x = x >= 0 ? x : 0 , p).trim() ;
			String subStrPesq = pesquisa.substring(m = m >= 0 ? m : 0, pesquisa.length()).trim();
			String strVolta = pesquisa+" "+autoComplete.getChave().substring(p , (autoComplete.getChave()+"").length());
			
			if(subFrases.equals(subStrPesq)) {
				subFrases =	autoComplete.getChave().substring(autoComplete.getChave().substring(0, x).trim().lastIndexOf(" ") >= 0 ? autoComplete.getChave().substring(0, x).trim().lastIndexOf(" ") : 0, x).trim();
				subStrPesq = pesquisa.substring(pesquisa.substring(0, m).trim().lastIndexOf(" ") >= 0 ? pesquisa.substring(0, m).trim().lastIndexOf(" ") : 0 , m).trim();
				
 				if(subFrases.equals(subStrPesq) && c < maxResults) {
 					if(!(preferencia1.contains(strVolta)) == true) {
 						preferencia1.add(c , strVolta);
 						c++;
 					}
 				}
 				if(a < maxResults) {
 					if(!(preferencia2.contains(strVolta)) == true) {
 						preferencia2.add(a , strVolta);
 						a++; 
 					}
 				}
			} else if(b < maxResults) {
				if(!(preferencia3.contains(strVolta)) == true) {
					preferencia3.add(b , strVolta);
					b++;
				}
			}
		}
		
		if (preferencia1.size() != 0) {
			preferencia1.add(0, inicial);
			return preferencia1;
			
		} else if (preferencia2.size() != 0) {
			preferencia2.add(0, inicial);
			return preferencia2;
			
		} else {
			preferencia3.add(0, inicial);
			return preferencia3;
		}
	}
	
	/**
	 * Método responsável retornar a similaridade mas equivalente a palavra inserida
	 * 
	 * @param palavra - String
	 * @return palavra - String
	 */
	private String listaProvaveisSimilaridades(String palavra) throws Exception {
		List<String> lista = autoCompleteRepository.searchChaves();
		List<ComportamentoVO> listSimilaridades = new ArrayList<>();
		float j;
		for (String palavraAutoComplete : lista) {
			if ((j = checaSimilaridade(palavraAutoComplete, palavra)) > 0.80) {
				ComportamentoVO comportamento = new ComportamentoVO(palavraAutoComplete, j);
				listSimilaridades.add(comportamento);
			}
		}
		
		if (listSimilaridades.size() > 0) {
			if (listSimilaridades.size() == 0) {
				return listSimilaridades.get(0).getComportamento();
			} else { 
				ordenaListaCrescente(listSimilaridades);
				return listSimilaridades.get(0).getComportamento();
			}
		} else {
			return "";
		}
		
	}
	
	/**
	 * Método responsável por ordenar a lista de palavras pela similaridade entre elas
	 * 
	 * @param preferencia2 - List<ComportamentoVO>
	 */
	private void ordenaListaCrescente(List<ComportamentoVO> preferencia2) {
		Collections.sort(preferencia2, new Comparator<ComportamentoVO>() {
		    @Override
		    public int compare(ComportamentoVO auto2, ComportamentoVO auto1) {
		    	if (auto1.getSimilaridade().equals(auto2.getSimilaridade())) {
		    		if (auto1.getComportamento().length() == auto2.getComportamento().length()) {
		    			return 1;
		    		}
		    		return auto2.getComportamento().length() - auto1.getComportamento().length();
		    	} else {
		    		return Integer.parseInt((auto1.getSimilaridade()*100)+"") - Integer.parseInt((auto2.getSimilaridade()*100)+"");
		    	}
		    }
		});
	}
	
	/**
	 * Método responsável receber duas palavras e retonar o numero de similaridade entre as duas
	 * 
	 * @param sString1 - String
	 * @param sString2 - String
	 * @return similaridade - float
	 */
	private static float checaSimilaridade(String sString1, String sString2) throws Exception {
        if (sString1.length() != sString2.length()) {
            int iDiff = Math.abs(sString1.length() - sString2.length());
            int iLen = Math.max(sString1.length(), sString2.length());
            String sBigger, sSmaller, sAux;

            if (iLen == sString1.length()) {
                sBigger = sString1;
                sSmaller = sString2;
            } else {
                sBigger = sString2;
                sSmaller = sString1;
            }

            float fSim, fMaxSimilarity = Float.MIN_VALUE;
            for(int i = 0; i <= sSmaller.length(); i++) {
                sAux = sSmaller.substring(0, i) + sBigger.substring(i, i+iDiff) + sSmaller.substring(i);
                fSim = checaSimilaridadePeloTamanho(sBigger,  sAux);
                if (fSim > fMaxSimilarity) {
                    fMaxSimilarity = fSim;
                }
            }
            return fMaxSimilarity - (1f * iDiff) / iLen;

        } else {
        	return checaSimilaridadePeloTamanho(sString1, sString2);
        }
    }

	/**
	 * Método responsável receber duas palavras e retonar o numero de similaridade referente ao tamanha entre as duas
	 * 
	 * @param sString1 - String
	 * @param sString2 - String
	 * @return similaridade - float
	 */
    private static float checaSimilaridadePeloTamanho(String sString1, String sString2) throws Exception {

        if (sString1.length() != sString2.length()) {
        	throw new Exception("Strings devem ter o mesmo tamanho!");
        }

        int iLen = sString1.length();
        int iDiffs = 0;

        for(int i = 0; i < iLen; i++) {
        	if (sString1.charAt(i) != sString2.charAt(i)) {
        		iDiffs++;
        	}
        }

        return 1f - (float) iDiffs / iLen;
    }
}
