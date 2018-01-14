package put.io.black.java.core.rest;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import put.io.black.java.core.logic.ScenarioManager;

/**
 * REST API controller
 * @see ScenarioManager
 */
@RestController
public class ScenarioController {

    /**
     * Controller's logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ScenarioController.class);

    /**
     * Get scenario with numeric
     * @param body Scenario without numeric
     * @return Scenario with numeric
     */
    @RequestMapping(value = "/numeric", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioWithNumeric(@RequestBody String body) {
        // Logs
        logger.info("POST scenario with numeric");
        logger.debug(body);

        boolean success = false;
        String result = "";

        // Parser
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        if(scenarioElement != null) {
            String scenario = scenarioElement.getAsString();

            // Log scenario
            logger.debug(scenario);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.getScenarioWithNumeration();

            success = true;
        }

        // Response
        JsonObject response = new JsonObject();
        if(success) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }

    /**
     * Get scenario in base form
     * @param body Test of scenario
     * @return Scenario in base form
     */
    @RequestMapping(value = "scenario", method = RequestMethod.POST, produces = "application/json")
    public String getScenario(@RequestBody String body) {
        // Logs
        logger.info("POST scenario basic");
        logger.debug(body);

        boolean success = false;
        String result = "";

        // Parser
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        if(scenarioElement != null) {
            String scenario = scenarioElement.getAsString();

            // Log scenario
            logger.debug(scenario);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.getScenario();

            success = true;
        }

        // Response
        JsonObject response = new JsonObject();
        if(success) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }

    /**
     * Scenario without actors
     * @param body Scenario text
     * @return Scenario without actors
     */
    @RequestMapping(value = "/without_actors", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioWithoutActors(@RequestBody String body) {
        // Logs
        logger.info("POST scenario without actors");
        logger.debug(body);

        boolean success = false;
        String result = "";

        // Parser
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        if(scenarioElement != null) {
            String scenario = scenarioElement.getAsString();

            // Log scenario
            logger.debug(scenario);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.cutActorsFromScenario();

            success = true;
        }

        // Response
        JsonObject response = new JsonObject();
        if(success) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }

    /**
     * Count keywords in scenario
     * @param body Scenario text
     * @return Amount of keywords in scenario
     */
    @RequestMapping(value = "/number_keywords", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioNumberKeyWords(@RequestBody String body) {
        // Logs
        logger.info("POST count keywords in scenario");
        logger.debug(body);

        boolean success = false;
        Integer result = 0;

        // Parser
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        if(scenarioElement != null) {
            String scenario = scenarioElement.getAsString();

            // Log scenario
            logger.debug(scenario);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.countKeyWordsInScenario();

            success = true;
        }

        // Response
        JsonObject response = new JsonObject();
        if(success) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }

    /**
     * Count steps in scenario
     * @param body Scenario text
     * @return Amount of steps in scenario
     */
    @RequestMapping(value = "/steps", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioSteps(@RequestBody String body) {
        // Logs
        logger.info("POST count scenario steps");
        logger.debug(body);

        boolean success = false;
        Integer result = 0;

        // Parser
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        if(scenarioElement != null) {
            String scenario = scenarioElement.getAsString();

            // Log scenario
            logger.debug(scenario);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.countNumberOfScenarioSteps();

            success = true;
        }

        // Response
        JsonObject response = new JsonObject();
        if(success) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }

    /**
     * Check maximum nesting level in scenario
     * @param body Scenario text
     * @return Integer with maximum nesting level
     */
    @RequestMapping(value = "/nesting", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioNesting(@RequestBody String body) {
        // Logs
        logger.info("POST count scenario nesting");
        logger.debug(body);

        boolean success = false;
        Integer result = 0;

        // Parser
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        if(scenarioElement != null) {
            String scenario = scenarioElement.getAsString();

            // Log scenario
            logger.debug(scenario);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.countScenarioNesting();

            success = true;
        }

        // Response
        JsonObject response = new JsonObject();
        if(success) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }

    /**
     * Scenario to limit nesting level
     * @param body Scenario text
     * @return Scenario with limit of nesting level
     */
    @RequestMapping(value = "/level", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioToLevel(@RequestBody String body) {
        // Logs
        logger.info("POST scenario to nesting level");
        logger.debug(body);

        boolean success = false;
        boolean correctLevel = false;
        int level = 0;
        String result = "";

        // Level
        JsonElement levelElement = new JsonParser().parse(body).getAsJsonObject().get("level");
        if(levelElement != null) {
            // Try cast to int
            level = levelElement.getAsInt();

            // Log
            logger.debug(Integer.toString(level));

            correctLevel = true;
        }

        // Scenario
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        if(correctLevel && scenarioElement != null) {
            String scenario = scenarioElement.getAsString();

            // Log scenario
            logger.debug(scenario);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.getScenario(level);

            success = true;
        }

        // Response
        JsonObject response = new JsonObject();
        if(correctLevel && success) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else if(!correctLevel) {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing level field in body.");
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }
}
