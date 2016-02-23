package net.entangledmedia.vertxreact

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import org.junit.Before
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class SimpleRESTTest {

    static Vertx vertx
    static SimpleREST simpleREST

    static ConfigObject config
    static def verticleId = null

    @BeforeClass
    static void setup() {

        //config = new ConfigSlurper('functionalTesting').parse(this.class.getResourceAsStream('/test-config.groovy').text)
        //DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject(JsonOutput.toJson(config)));

        VertxOptions vertxOptions = new VertxOptions();

        // Lets not worry about blocking the main thread during testing
        vertxOptions.blockedThreadCheckInterval = 1000*60*60;

        // Create the vertx instance
        vertx = Vertx.vertx(vertxOptions)

        log.info("Deploying verticle")

        simpleREST = new SimpleREST()

        // deploy the verticle
        vertx.deployVerticle(simpleREST, { res ->
            if (res.succeeded()) {
                log.info("Deployment id is: " + res.result());
                verticleId = res.result()
            } else {
                log.info("Deployment failed!");
                System.exit(-1)
            }
        });

        // Create a timer to quit if the verticle fails to deploy
        long timerId = vertx.setTimer(30000, { timerId ->
            fail("Verticle failed to deploy")
            System.exit(-1)
        })

        // Wait and see if it deployed
        log.info("Waiting")
        while(verticleId == null) {
            log.info("Waiting")
            Thread.sleep(500)
        }

        vertx.cancelTimer(timerId)

        log.info("verticle launched")
    }

    @Test
    public void testLoadRoutes() throws Exception {

        def routes = simpleREST.router.getRoutes()

        routes.each {
            log.info it.toString()
        }


    }
}