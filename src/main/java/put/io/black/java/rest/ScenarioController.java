package put.io.black.java.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import put.io.black.java.logic.ScenarioManager;

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
    private static final String ERROR_MESSAGE = "Second param is not number.";

    /**
     * Get scenario with numeric
     * @param text Scenario without numeric
     * @return Scenario with numeric
     */
    @RequestMapping(value = "/numeric/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioWithNumeric(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenarioWithNumeration();
    }

    /**
     * Get scenario in base form
     * @param text Test of scenario
     * @return Scenario in base form
     */
    @RequestMapping(value = "/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenario(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenario();
    }

    /**
     * Scenario without actors
     * @param text Scenario text
     * @return Scenario without actors
     */
    @RequestMapping(value = "/without_actors/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioWithoutActors(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.cutActorsFromScenario();
    }

    /**
     * Count keywords in scenario
     * @param text Scenario text
     * @return Amount of keywords in scenario
     */
    @RequestMapping(value = "/number_keywords/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioNumberKeyWords(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countKeyWordsInScenario());
    }

    /**
     * Count steps in scenario
     * @param text Scenario text
     * @return Amount of steps in scenario
     */
    @RequestMapping(value = "/steps/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioSteps(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countNumberOfScenarioSteps());
    }

    /**
     * Check maximum nesting level in scenario
     * @param text Scenario text
     * @return Integer with maximum nesting level
     */
    @RequestMapping(value = "/nesting/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioNesting(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countScenarioNesting());
    }

    /**
     * Scenario to limit nesting level
     * @param text Scenario text
     * @param toLevel Nesting level limit
     * @return Scenario with limit of nesting level
     */
    @RequestMapping(value = "/level/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioToLevel(@PathVariable String text, @RequestParam(value = "level", defaultValue = "1") String toLevel) {
        logger.debug(text);
        logger.debug(toLevel);
        int level = 0;
        try {
            level = Integer.parseInt(toLevel);
        } catch (NumberFormatException e) {
            return ERROR_MESSAGE;
        }
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenario(level);
    }
}
