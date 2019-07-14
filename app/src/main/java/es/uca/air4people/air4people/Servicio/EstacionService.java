package es.uca.air4people.air4people.Servicio;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EstacionService {

        @GET("availableMotes")
        Call<List<Estacion>> getEstaciones();

        @GET("locateAllMotes")
        Call<List<Estacion>> getMapa();

        @GET("availablePollutants")
        Call<List<String>> getContaminantes();

        @GET("moteCurrentQAir/{name}")
        Call<List<Medicion>> getPredicciones(@Path("name") String name);

        @GET("moteDateQAir/{name}/{date}")
        Call<List<Medicion>> getPrediccionFecha(@Path("name") String name, @Path("date") String date);

}