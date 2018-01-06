package put.io.black.java.core.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import put.io.black.java.core.logic.ScenarioManager;

@RestController
public class ScenarioController {

    private static final Logger logger = LoggerFactory.getLogger(ScenarioController.class);
    private static final String ERROR_MESSAGE = "Second param is not number.";


    @RequestMapping(value = "/numeric/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioWithNumeric(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenarioWithNumeration();
    }

    @RequestMapping(value = "/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenario(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenario();
    }

    @RequestMapping(value = "/without_actors/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioWithoutActors(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.cutActorsFromScenario();
    }

    @RequestMapping(value = "/number_keywords/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioNumberKeyWords(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countKeyWordsInScenario());
    }

    @RequestMapping(value = "/steps/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioSteps(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countNumberOfScenarioSteps());
    }

    @RequestMapping(value = "/nesting/{text}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String getScenarioNesting(@PathVariable String text) {
        logger.debug(text);
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countScenarioNesting());
    }

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
