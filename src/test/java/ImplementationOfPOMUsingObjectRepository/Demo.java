package ImplementationOfPOMUsingObjectRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {
    private static final Logger logger = LogManager.getLogger(Demo.class);

    public static void main(String[] args) {
        logger.info("INFO log working");
        logger.warn("WARN log working");
        logger.error("ERROR log working");
    }
}