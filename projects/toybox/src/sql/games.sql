/**
 * $Id$
 *
 * Schema for the GAMES table.
 */

drop table if exists GAMES;

/**
 * Every game published by the system 
 */
CREATE TABLE GAMES
(
    /**
     * A unique integer identifier for this game.
     */
    GAME_ID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,

    /**
     * The user id of the maintainer.
     */
    MAINTAINER_ID INTEGER UNSIGNED NOT NULL,

    /**
     * The status of this game (whether or not it should be published, if
     * it is flagged for review, etc.).
     */
    STATUS VARCHAR(255) NOT NULL,

    /** 
     * The server on which this game is hosted.
     */
    HOST VARCHAR(255) NOT NULL,

    /** 
     * The current version's XML definition.
     */
    DEFINITION TEXT NOT NULL,

    /** 
     * The MD5 digest of the game jar file.
     */
    DIGEST VARCHAR(255) NOT NULL,

    /** 
     * The current test version's XML definition.
     */
    TEST_DEFINITION TEXT NOT NULL,

    /** 
     * The MD5 digest of the test game jar file.
     */
    TEST_DIGEST VARCHAR(255) NOT NULL,

    /** 
     * A short description of the game.
     */
    DESCRIPTION TEXT NOT NULL,

    /** 
     * Brief instructions on how to play the game.
     */
    INSTRUCTIONS TEXT NOT NULL,

    /**
     * Define our table keys.
     */
    KEY (MAINTAINER_ID),
    PRIMARY KEY (GAME_ID)
);
