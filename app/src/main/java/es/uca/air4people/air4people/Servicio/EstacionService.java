package es.uca.air4people.air4people.Servicio;
import java.util.List;
import es.uca.air4people.air4people.Servicio.Estacion;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EstacionService {
        String API_ROUTE = "locateAllMotes";
        @GET(API_ROUTE)
        Call<List<Estacion>> getMapa();
}