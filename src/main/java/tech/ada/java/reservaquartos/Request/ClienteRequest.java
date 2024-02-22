package tech.ada.java.reservaquartos.Request;

import lombok.Getter;
import lombok.Setter;

//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//teste
@Getter
@Setter
public class ClienteRequest {
 private String nomeCompleto;
 private String cpf;
 private String cep;
 private String endereco;
 private String telefone;
 private String email;
//
//        private static final String URL_VIACEP = "https://viacep.com.br/ws/";
//
//        public static boolean validarCEP(String cep) {
//            if (cep == null || cep.isEmpty() || !cep.matches("\\d{8}")) {
//                return false;
//            }
//
//            try {
//                CloseableHttpClient httpClient = HttpClients.createDefault();
//
//                HttpGet httpGet = new HttpGet(URL_VIACEP + cep + "/json/");
//
//                HttpResponse httpResponse = httpClient.execute(httpGet);
//
//                if (httpResponse.getStatusLine().getStatusCode() != 200) {
//                    return false;
//                }
//
//                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
//
//                JsonObject jsonObject = new JsonParser().parse(jsonResponse).getAsJsonObject();
//
//                if (jsonObject.has("erro") && jsonObject.get("erro").getAsBoolean()) {
//                    return false;
//                }
//
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }


}
