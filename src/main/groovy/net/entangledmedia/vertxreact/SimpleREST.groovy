package net.entangledmedia.vertxreact

import groovy.util.logging.Slf4j
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SimpleREST extends AbstractVerticle {

    private Map<String, JsonObject> products = new HashMap<>();

    ConfigObject routesConfig;

    Router router;

    public void loadRoutes(router, routeFile = '/routes.groovy') {

        String routesConfigText =  getClass().getResourceAsStream(routeFile)?.text

        if (routesConfigText == null) {
            throw new Exception("Unable to load routes.groovy file from classpath")
        }

        def routesConfig = new ConfigSlurper().parse(routesConfigText)

        def index = routesConfig.index

        routesConfig.routes.each { route ->

            String path = route[index.path]
            String method = route[index.method].toLowerCase()
            String handler = route[index.handler]

            log.info("Loading route: ${method} ${path} --> ${handler}")

            router."${method}"(path).handler(this.&"$handler")

        }
    }

    @Override
    public void start() {

        router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        loadRoutes(router)

        vertx.createHttpServer().requestHandler(router.&accept).listen(8080);
    }

    private void handleGetProduct(RoutingContext routingContext) {
    }

    private void handleAddProduct(RoutingContext routingContext) {
    }

    private void handleListProducts(RoutingContext routingContext) {
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

}
