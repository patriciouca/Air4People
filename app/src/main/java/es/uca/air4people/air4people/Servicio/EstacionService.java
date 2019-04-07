package es.uca.air4people.air4people.Servicio;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EstacionService {
        @GET("locateAllMotes")
        Call<List<Estacion>> getMapa();

        @GET("moteCurrentQAir/{name}")
        Call<List<Medicion>> getPredicciones(@Path("name") String name);

        @GET("moteDateQAir/{name}/{date}")
        Call<List<Medicion>> getPrediccionFecha(@Path("name") String name, @Path("date") String date);

}