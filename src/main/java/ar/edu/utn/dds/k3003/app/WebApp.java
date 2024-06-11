package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.client.LogisticaProxy;
import ar.edu.utn.dds.k3003.client.ViandasProxy;
import ar.edu.utn.dds.k3003.controller.ColaboradorController;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class WebApp {
  public static void main(String[] args) {

      Fachada fachada = new Fachada();
      var env = System.getenv();
      var port = Integer.parseInt(env.getOrDefault("PORT", "8080"));
      var app = Javalin.create().start(port);
      var objectMapper = createObjectMapper();

      fachada.setViandasProxy(new ViandasProxy(objectMapper));
      fachada.setLogisticaProxy(new LogisticaProxy(objectMapper));

      ColaboradorController colaboradorController = new ColaboradorController(fachada);

      app.get("/", ctx -> ctx.result("Hello World"));
      app.get("/colaboradores/{id}", colaboradorController::getColaborador);
      app.post("/colaboradores", colaboradorController::crearColaborador);
      app.patch("/colaboradores/{id}", colaboradorController::modificarColaborador);
      app.get("/colaboradores/{id}/puntos", colaboradorController::getPuntuacionColaborador);
      app.put("/formula", colaboradorController::modificarPuntuacionMultiplicador);
    }

    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper;
    }

    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);
    }
}
