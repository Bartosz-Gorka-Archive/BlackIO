package put.io.black.java.core.rest;

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
     * Default error message in invalid nesting level request
     */
    private static final String ERROR_MESSAGE = "Nesting level is not a integer!";

    /**
     * Get scenario with numeric
     * @param body Scenario without numeric
     * @return Scenario with numeric
     */
    @RequestMapping(value = "/numeric", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioWithNumeric(@RequestBody String body) {
        logger.info("POST scenario with numeric");
        logger.debug(body);
        ScenarioManager scenarioManager = new ScenarioManager(body);
        return scenarioManager.getScenarioWithNumeration();
    }

    /**
     * Get scenario in base form
     * @param body Test of scenario
     * @return Scenario in base form
     */
    @RequestMapping(value = "scenario", method = RequestMethod.POST, produces = "application/json")
    public String getScenario(@RequestBody String body) {
        logger.info("POST scenario");
        logger.debug(body);
        ScenarioManager scenarioManager = new ScenarioManager(body);
        return scenarioManager.getScenario();
    }

    /**
     * Scenario without actors
     * @param body Scenario text
     * @return Scenario without actors
     */
    @RequestMapping(value = "/without_actors", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioWithoutActors(@RequestBody String body) {
        logger.info("POST scenario without actors");
        logger.debug(body);
        ScenarioManager scenarioManager = new ScenarioManager(body);
        return scenarioManager.cutActorsFromScenario();
    }

    /**
     * Count keywords in scenario
     * @param body Scenario text
     * @return Amount of keywords in scenario
     */
    @RequestMapping(value = "/number_keywords", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioNumberKeyWords(@RequestBody String body) {
        logger.info("POST scenario count keyword");
        logger.debug(body);
        ScenarioManager scenarioManager = new ScenarioManager(body);
        return Integer.toString(scenarioManager.countKeyWordsInScenario());
    }

    /**
     * Count steps in scenario
     * @param body Scenario text
     * @return Amount of steps in scenario
     */
    @RequestMapping(value = "/steps", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioSteps(@RequestBody String body) {
        logger.info("POST scenario steps");
        logger.debug(body);
        ScenarioManager scenarioManager = new ScenarioManager(body);
        return Integer.toString(scenarioManager.countNumberOfScenarioSteps());
    }

    /**
     * Check maximum nesting level in scenario
     * @param body Scenario text
     * @return Integer with maximum nesting level
     */
    @RequestMapping(value = "/nesting", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioNesting(@RequestBody String body) {
        logger.info("POST scenario nesting level");
        logger.debug(body);
        ScenarioManager scenarioManager = new ScenarioManager(body);
        return Integer.toString(scenarioManager.countScenarioNesting());
    }

    /**
     * Scenario to limit nesting level
     * @param body Scenario text
     * @param toLevel Nesting level limit
     * @return Scenario with limit of nesting level
     */
    @RequestMapping(value = "/level", method = RequestMethod.POST, produces = "application/json")
    public String getScenarioToLevel(@RequestBody String body, @RequestParam(value = "level", defaultValue = "1") String toLevel) {
        logger.info("POST scenario TO nesting level");
        logger.debug(body);
        logger.debug(toLevel);
        int level = 0;
        try {
            level = Integer.parseInt(toLevel);
        } catch (NumberFormatException e) {
            return ERROR_MESSAGE;
        }
        ScenarioManager scenarioManager = new ScenarioManager(body);
        return scenarioManager.getScenario(level);
    }
}
