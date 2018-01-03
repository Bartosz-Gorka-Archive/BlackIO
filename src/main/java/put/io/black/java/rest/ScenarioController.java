package put.io.black.java.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import put.io.black.java.logic.ScenarioManager;
import put.io.black.java.logic.TextTransformer;

import java.util.Arrays;

@RestController
public class ScenarioController {

    private static final Logger logger = LoggerFactory.getLogger(ScenarioController.class);

    @RequestMapping(value = "/transforms/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String transform(@PathVariable String text, @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        TextTransformer transformer = new TextTransformer(transforms);
        return transformer.transform(text);
    }

    @RequestMapping(value = "/numeric/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String getScenarioWithNumeric(@PathVariable String text, @RequestParam(value="numeric", defaultValue="") String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenarioWithNumeration();
    }

    @RequestMapping(value = "/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String getScenario(@PathVariable String text, @RequestParam(value="", defaultValue="") String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenario();
    }

    @RequestMapping(value = "/without_actors/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String getScenarioWithoutActors(@PathVariable String text, @RequestParam(value="withoutActors", defaultValue="") String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.cutActorsFromScenario();
    }

    @RequestMapping(value = "/number_keywords/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String getNumberKeyWords(@PathVariable String text, @RequestParam(value="numberKeyWords", defaultValue="") String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countKeyWordsInScenario());
    }

    @RequestMapping(value = "/steps/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String getScenarioSteps(@PathVariable String text, @RequestParam(value="steps", defaultValue="") String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countNumberOfScenarioSteps());
    }

    @RequestMapping(value = "/nesting/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String getScenarioNesting(@PathVariable String text, @RequestParam(value="nesting", defaultValue="") String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return Integer.toString(scenarioManager.countScenarioNesting());
    }

    @RequestMapping(value = "/level/{text}", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
    public String getScenarioToLevel(@PathVariable String text, @RequestParam(value="level", defaultValue="1") String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));
        int level = 0;
        try {
            level = Integer.parseInt(transforms[0]);
        }catch (NumberFormatException e){
            logger.debug(e.toString());
            return "";
        }
        ScenarioManager scenarioManager = new ScenarioManager(text);
        return scenarioManager.getScenario(level);
    }
}
