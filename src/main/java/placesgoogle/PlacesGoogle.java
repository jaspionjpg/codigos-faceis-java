package placesgoogle;

import distancialatitude.DistanciaLatitude;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingInt;

public class PlacesGoogle {

    public List<PlacesVO> getPlaces(String latitude, String longitude, List<String> pontos){
        List<PlacesVO> places = new ArrayList<>();

        String pontosFormatado = null;
        if (pontos != null) {
            pontos = changeToStringList(pontos);
            pontosFormatado = pontos.get(0);
            for (int i = 1 ; i < pontos.size(); i++) {
                pontosFormatado += "|" + pontos.get(i);
            }
        } else {
            return places;
        }

        JSONObject jsonp = null;
        String urlFormatada = null;

        urlFormatada = MessageFormat.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={0},{1}&radius=500&types={2}&key={3}", new Object[] { latitude,
                longitude,
                pontosFormatado,
                "<GOOGLE_GEOCODING_API_KEY>" });

        String json = retornoUrl(urlFormatada);

        jsonp = JSONObject.fromObject(json);
        if (jsonp != null && jsonp.getString("status").equals("OK")) {
            JSONArray data = (JSONArray) jsonp.get("results");

            for (int i = 0; i < data.size(); i++) {
                JSONObject result = (JSONObject) data.get(i);
                JSONObject geometry = JSONObject.fromObject(result.getString("geometry"));
                JSONObject location = JSONObject.fromObject(geometry.getString("location"));

                PlacesVO  place = new PlacesVO();
                place.setLatitude(location.getString("lat"));
                place.setLongitude(location.getString("lng"));
                place.setDistanciaDoEndereco(DistanciaLatitude.getDistancia(Double.valueOf(latitude), Double.valueOf(place.getLatitude()), Double.valueOf(longitude), Double.valueOf(place.getLongitude())));

                place.setIcone(result.getString("icon"));
                place.setIdPlace(result.getString("place_id"));
                place.setNome(result.getString("name"));
                place.setEndereco(result.getString("vicinity"));
                String[] listaDeTipos = (result.getString("types").replace("\"", "").replace("[", "").replace("]", "").split(","));

                for (String tipo : listaDeTipos) {
                    for (String tipoDisponivel : pontos) {
                        if (tipo.equals(tipoDisponivel)) {
                            place.setTipo(tipo);
                            break;
                        }
                    }
                }

                places.add(place);
            }
        }
        sortPlaces(places);
        return places;
    }

    public void sortPlaces(List<PlacesVO> places) {
        places.sort(comparingInt(place -> place.getDistanciaDoEndereco().intValue()));
    }

    /**
     * Método responsável por realizar uma chamada URL e retornar o valor
     *
     * @param urlString - String
     * @return - String
     */
    public static String retornoUrl(String urlString) {
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(urlString);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

            String retornoJson;
            while ((retornoJson = bufferedReader.readLine()) != null) builder.append(retornoJson);

            bufferedReader.close();

        } catch (MalformedURLException e) {
//            Log.getInstance(Thread.currentThread().getStackTrace()).error(Messager.getMessage("comunicacao.notification.sms.url.mal.formatada"), e);
        } catch (IOException e) {
//            Log.getInstance(Thread.currentThread().getStackTrace()).error(Messager.getMessage("comunicacao.notification.sms.url.falha.ao.enviar"), e);
        }

        return builder.toString();
    }

    /**
     * Método responsável por transformar em uma lista de String
     *
     * @return listaString - List<String>
     */
    public static List<String> changeToStringList(List<String> lista) {
        List<String> listaString = new ArrayList<String>();
        if (lista != null) {
            for (String valor : lista) {
                String[] string = valor.split(",");
                for (int i = 0; i < string.length; i++) {
                    listaString.add(string[i].trim());
                }
            }
        }
        return listaString;
    }

}
