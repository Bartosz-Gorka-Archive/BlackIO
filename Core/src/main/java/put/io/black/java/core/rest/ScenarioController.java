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
     * Save scenario in base
     * @param body JSON body with title and scenario fields
     * @return Status of save action
     */
    @RequestMapping(value = "save_scenario", method = RequestMethod.POST, produces = "application/json")
    public String saveScenario(@RequestBody String body) {
        // Logs
        logger.info("POST scenario save");
        logger.debug(body);
        String result = "";

        // Parser
        JsonElement scenarioElement = new JsonParser().parse(body).getAsJsonObject().get("scenario");
        JsonElement titleElement = new JsonParser().parse(body).getAsJsonObject().get("title");
        if(scenarioElement != null && titleElement != null) {
            String scenario = scenarioElement.getAsString();
            String title = titleElement.getAsString();

            // Log scenario
            logger.debug(scenario);
            logger.debug(title);

            // Calculate
            ScenarioManager scenarioManager = new ScenarioManager(scenario);
            result = scenarioManager.saveScenarioToFile(title);
        }

        // Response
        JsonObject response = new JsonObject();
        if(scenarioElement == null) {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        } else if(titleElement == null) {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing title field in body.");
        } else if(result.equals("File was saved.")) {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", result);
        }
        return response.toString();
    }

    /**
     * Read scenario from API
     * @param body JSON body with scenario title
     * @return Scenario or message about not existing file
     */
    @RequestMapping(value = "read_scenario", method = RequestMethod.POST, produces = "application/json")
    public String readScenario(@RequestBody String body) {
        logger.info("POST scenario read file");
        logger.debug(body);
        String result = "";

        JsonElement titleElement = new JsonParser().parse(body).getAsJsonObject().get("title");
        if(titleElement != null) {
            String title = titleElement.getAsString();
            logger.debug(title);

            ScenarioManager scenarioManager = new ScenarioManager();
            result = scenarioManager.readScenario(title);
        }

        JsonObject response = new JsonObject();
        if(titleElement == null) {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing title field in body.");
        } else if(result.equals("File not exist.") || result.equals("File can't be read.")) {
            response.addProperty("status", "error");
            response.addProperty("message", result);
        } else {
            response.addProperty("status", "success");
            response.addProperty("result", result);
        }
        return response.toString();
    }

    /**
     * Listing scenarios already inserted to application
     * @return Status of save action
     * @since 1.8
     */
    @RequestMapping(value = "listing_scenarios", method = RequestMethod.POST, produces = "application/json")
    public String listingScenarios() {
        logger.info("POST scenario listing files");

        ScenarioManager scenarioManager = new ScenarioManager();
        String[] result = scenarioManager.getListScenarioSaved();

        JsonObject response = new JsonObject();
        if(result.length == 1 && result[0].equals("")) {
            response.addProperty("status", "error");
            response.addProperty("message", "None inserted scenarios.");
        } else {
            response.addProperty("status", "success");
            response.addProperty("result", String.join("\n", result));
        }

        return response.toString();
    }

    /**
     * Get scenario in base form
     * @param body Text of scenario
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
            try {
                level = levelElement.getAsInt();

                // Log
                logger.debug(Integer.toString(level));

                correctLevel = true;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
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
            response.addProperty("message", "Missing level field with correct number in body.");
        } else {
            response.addProperty("status", "error");
            response.addProperty("message", "Missing scenario field in body.");
        }
        return response.toString();
    }
}
