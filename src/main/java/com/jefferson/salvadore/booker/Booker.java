/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker;

import static com.jefferson.salvadore.booker.publication.PubFreq.NA;
import static com.jefferson.salvadore.booker.publication.PubFreq.pubFreqOf;
import static com.jefferson.salvadore.booker.publication.Range.of;
import static java.net.HttpURLConnection.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.publication.Publication;
import com.jefferson.salvadore.booker.repo.PublicationManager;
import com.jefferson.salvadore.booker.repo.PublicationManagerImpl;
import com.jefferson.salvadore.booker.repo.PublicationRepository;
import com.jefferson.salvadore.booker.repo.file.FilePublicationRepository;
import com.jefferson.salvadore.booker.repo.snapshot.SnapshotTopPublicationRepository;
import com.jefferson.salvadore.booker.util.IO;

/**
 * <p>
 * The "Booker Application" utilizes the {@link PublicationRepositiry} interface to perform actions
 * on a data store of {@link Publication} objects.
 * </p>
 * 
 * @version 39.0.0 1-05-2016
 * 
 * @author Salvadore Jefferson
 * 
 */
public class Booker {

  public static final Path CONFIG_DIR = Paths.get(System.getProperty("user.home"), ".booker");
  private static final PublicationRepository REPO = new SnapshotTopPublicationRepository();

  public static int issueCount;
  public static boolean help;
  public static boolean type;
  public static boolean verbose;
  public static boolean debug;
  public static boolean list;
  public static boolean purchase;
  public static boolean subscribe;
  public static boolean lookUp;
  public static boolean info;
  public static String title;
  public static String typeList;
  public static String isbn;

  static final Logger logger = LoggerFactory.getLogger(Booker.class);

  public static void main(String[] args) {

    final Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    ((ch.qos.logback.classic.Logger) rootLogger).setLevel(ch.qos.logback.classic.Level.WARN);

    final CommandLineOptions commandLineOpts = new CommandLineOptions();
    final CmdLineParser parser = new CmdLineParser(commandLineOpts);
    // final Injector injector = Guice.createInjector(new PublicationModule());
    // final PublicationManager manager = injector.getInstance(PublicationManager.class);
    final PublicationManager manager = new PublicationManagerImpl(REPO);
    final BookerServices service = new BookerServicesImpl(manager);

    try {
      parser.parseArgument(args);
      issueCount = commandLineOpts.issueCount();
      help = commandLineOpts.help();
      type = commandLineOpts.filterByType();
      verbose = commandLineOpts.verbose();
      debug = commandLineOpts.debug();
      list = commandLineOpts.list();
      purchase = commandLineOpts.purchase();
      subscribe = commandLineOpts.subscribe();
      title = commandLineOpts.identifier();
      typeList = commandLineOpts.typeList();
      info = commandLineOpts.info();
      lookUp = commandLineOpts.lookUp();
      isbn = commandLineOpts.identifier();

      if (args.length <= 0) {
        logger.warn("you did not enter any options. The help menu will be displayed");
        parser.printUsage(System.out);
      }
      if (debug) {
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(ch.qos.logback.classic.Level.DEBUG);
        commandLineOpts.verbose = false;
        logger.info("The current log level is set to DEBUG mode");
      }
      if (verbose) {
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(ch.qos.logback.classic.Level.INFO);
        logger.info("You have selected --verbose mode");
      }
      if (list) {
        logger.info("You have asked for all available publications");
        REPO.printAllPubs();
      }
      if (type) {
        try {
          logger.info("You have requested a list of the following publications {} ", typeList);
          service.printPubByType(REPO, typeList);
        } catch (IOException e) {
          logger.error("An error occured will accessing a repository", e);
        }
      }
      if (purchase) {
        try {
          service.purchasePublication(title, 1);
          logger.info("You just purchased a copy of: {} ", title);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (subscribe) {
        try {
          service.subscribeToPeriodical(REPO, title, issueCount);
          logger.info("You have just subscribed to: {} ", title);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (info) {
        if (!lookUp) {
          logger.info("You must provide an additional argument for this command: <--lookup>");
        } else {
          try {
            logger.info("you have searched for {}", isbn);
            service.openConnectionToGoogleBooksAPI(isbn);
          } catch (MalformedURLException e1) {
            e1.printStackTrace();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      }

    } catch (final CmdLineException cmdLineException) {
      System.err.println(cmdLineException.getMessage());
      parser.printUsage(System.err);
      System.exit(1);
    }

    try {
      logger.info(IO.readText(Files.newInputStream(CONFIG_DIR.resolve("MA.txt"))));

      logger.info(IO.readText(Files.newInputStream(CONFIG_DIR.resolve("touché.txt"))));

      logger.info(IO.readText(Files.newInputStream(CONFIG_DIR.resolve("test_UTF16LE.txt"))));

      logger.info("The inventory count for Nature is {}", service.checkInventory("Nature"));
      logger.info("The inventory count for Nature Communications is {}",
          service.checkInventory("Nature Communications"));


    } catch (IOException e2) {
      logger.error("An error occured will accessing a repository", e2);

    }

    try {
      final FilePublicationRepository fileRepo =
          new FilePublicationRepository(CONFIG_DIR.resolve("publication-files"));

      // initialize repository
      fileRepo.initialize();

      // add a new Publication to fileRepo
      fileRepo.addPub(new Book("Test", "Test", of(0), 200_000_000, pubFreqOf(NA)));

      // make sure the new file is added
      logger.debug("{}", fileRepo.findByTitle("Test"));

      // test the deserialize method
      final Book book = (Book) fileRepo.deserializeFile("紅樓夢/红楼梦 (Dream of the Red Chamber)");
      logger.debug(book.getTitle());

    } catch (IOException | ClassCastException | ClassNotFoundException e2) {
      logger.error("An error occured will accessing a repository", e2);

    }
  }

  private static class CommandLineOptions {

    @Option(name = "--type", aliases = "-t", forbids = "--purchase", metaVar = "<List>",
        usage = "Restricts the listed publications to the type(S) specified. "
            + "Accepted type identifiers are book, magazine and journal. "
            + "Multiple types may be specified by concatinating into a single string separated by commas, e.g book, magazine")
    private boolean filterByType;

    @Option(name = "--help", aliases = "-h", metaVar = "<boolean>",
        usage = "Prints out a help summary of available switches.")
    private boolean help;

    @Option(name = "--verbose", aliases = "-v", metaVar = "<boolean>",
        usage = "Provide extra explanatory information in the output about the actions the program is executing.")
    private boolean verbose;

    @Option(name = "--debug", aliases = "-d", metaVar = "<boolean>",
        usage = "Sets the log level to debug.")
    private boolean debug;

    @Option(name = "--purchase", aliases = "-p", forbids = "--subscribe", metaVar = "<boolean>",
        usage = "Used to purchase Publications from the repository. example: --purchase Nature")
    private boolean purchase;

    @Option(name = "--subscribe", aliases = "-s", forbids = "--purchase", metaVar = "<boolean>",
        usage = "Used to subscribe to the specified number of copies to a Periodical from the repository. example: --subscribe Nature 5")
    private boolean subscribe;

    @Option(name = "--list", aliases = "-l", metaVar = "<boolean>",
        usage = "prints a list of all Publications from the repository.")
    private boolean list;

    @Option(name = "--info", aliases = "-i", metaVar = "<boolean>",
        usage = "Retrieves info on a particular book indicated by an acompanying isbn entered to the command line.")
    private boolean info;

    @Option(name = "--lookup", aliases = "-lu", metaVar = "<boolean>",
        usage = "used to initiate an http query from Google Books.")
    private boolean lookUp;

    @Option(name = "--isbn", metaVar = "<boolean>", usage = "The isbn of the title to query.")
    private boolean isbn;

    @Argument(index = 0,
        usage = "Argument to identify a title to the --purchase or --subscribe command, or for querying by ISBN")
    private String identifier;

    @Argument(index = 1,
        usage = "The second argument in the list should be the number of Periodical issues to subscribe to. example: --subscribe Nature 5")
    private int issueCount;

    private boolean help() {
      return help;
    }

    private boolean debug() {
      return debug;
    }

    private boolean verbose() {
      return verbose;
    }

    private boolean filterByType() {
      return filterByType;
    }

    private boolean purchase() {
      return purchase;
    }

    private boolean subscribe() {
      return subscribe;
    }

    private boolean list() {
      return list;
    }

    private boolean info() {
      return info;
    }

    private boolean lookUp() {
      return lookUp;
    }

    private boolean isbn() {
      return isbn;
    }

    private String identifier() {
      return identifier;
    }

    private String typeList() {
      return typeList;
    }

    public int issueCount() {
      return issueCount;
    }
  }
}
