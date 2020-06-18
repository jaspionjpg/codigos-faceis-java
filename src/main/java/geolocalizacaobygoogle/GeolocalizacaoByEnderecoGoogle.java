package geolocalizacaobygoogle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

public class GeolocalizacaoByEnderecoGoogle {

    /**
     * Método responsável por buscar o ponto de localização (latitude/longitude) do endereço indicado
     *
     * @param endereco - String
     * @return localizacao - double[]
     */
    public double[] getLocalizacaoEndereco(String endereco) {
        double[] localizacao = new double[2];

        try {
            endereco = " " + endereco;
            endereco = endereco.replace(" ", "+");

            String urlFormatada = MessageFormat.format("<https://maps.googleapis.com/maps/api/geocode/json?address={0}&key={1}>", new Object[] { endereco, "<GOOGLE_GEOCODING_API_KEY>" });

            URL url = new URL(urlFormatada);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;

            String json = "";
            while ((str = in.readLine()) != null) {
                json = json + str;
            }

            in.close();

            JSONObject jsonp = JSONObject.fromObject(json);

            if (jsonp != null && jsonp.getString("status").equals("OK")) {
                JSONArray data = (JSONArray) jsonp.get("results");

                for (int i = 0; i < data.size(); i++) {
                    JSONObject result = (JSONObject) data.get(i);
                    JSONObject geometry = JSONObject.fromObject(result.getString("geometry"));
                    JSONObject location = JSONObject.fromObject(geometry.getString("location"));

                    localizacao[0] = location.getDouble("lat");
                    localizacao[1] = location.getDouble("lng");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localizacao;
    }
}
