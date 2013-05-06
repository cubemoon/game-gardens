//
// ToyBox library - framework for matchmaking networked games
// Copyright (C) 2005-2012 Three Rings Design, Inc., All Rights Reserved
// http://github.com/threerings/game-gardens
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.toybox.server;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import com.google.common.base.Joiner;

import com.samskivert.util.Config;
import com.samskivert.util.ConfigUtil;
import com.samskivert.util.StringUtil;

import com.threerings.presents.client.Client;
import com.threerings.presents.server.Authenticator;
import com.threerings.presents.server.DummyAuthenticator;

import static com.threerings.toybox.Log.log;

/**
 * Provides access to installation specific configuration parameters. Once the {@link
 * ToyBoxManager} has been created, the {@link ToyBoxConfig} is initialized and becomes available
 * for use.
 */
public class ToyBoxConfig
{
    /** Provides access to our config properties. */
    public static Config config = readConfig();

    /**
     * Returns the main lobby server host.
     */
    public static String getServerHost ()
    {
        return config.getValue("server_host", "localhost");
    }

    /**
     * Returns the port on which our game servers are listening.
     */
    public static int getServerPort ()
    {
        return config.getValue("server_port", Client.DEFAULT_SERVER_PORTS[0]);
    }

    /**
     * Instantiates and returns the authenticator to be used by the server.
     */
    public static Class<? extends Authenticator> getAuthenticator ()
    {
        String authclass = config.getValue("server_auth", "");
        if (StringUtil.isBlank(authclass)) {
            return DummyAuthenticator.class;
        }

        try {
            @SuppressWarnings("unchecked") Class<? extends Authenticator> clazz =
                (Class<? extends Authenticator>)Class.forName(authclass);
            return clazz;
        } catch (Exception e) {
            log.warning("Failed to instantiate custom authenticator [class=" + authclass + "]", e);
            return null;
        }
    }

    /**
     * Returns the directory under which all resources are stored.
     */
    public static File getResourceDir ()
    {
        return new File(requireValue("resource_dir"));
    }

    /**
     * Returns the base URL via which all resources are downloaded.
     */
    public static String getResourceURL ()
    {
        return requireValue("resource_url");
    }

    /**
     * Returns the base URL via which the main website is accessed.
     */
    public static String getWebsiteURL ()
    {
        return requireValue("website_url");
    }

    /**
     * Returns the JDBC configuration for this installation.
     */
    public static Properties getJDBCConfig ()
    {
        return config.getSubProperties("db");
    }

    /** Helper function for warning on undefined config elements. */
    protected static String requireValue (String key)
    {
        String value = config.getValue(key, "");
        if (StringUtil.isBlank(value)) {
            log.warning("Missing required configuration '" + key + "'.");
        }
        return value;
    }

    protected static Config readConfig () {
        // if a properties file exists on the classpath, use it
        if (ConfigUtil.getStream(
                "toybox.properties", ToyBoxConfig.class.getClassLoader()) != null) {
            return new Config("toybox");
        }

        // otherwise create a default config for local testing
        log.info("No toybox.properties on classpath, using test config.");
        String[] defprops = {
            "resource_dir = target/games",
            "resource_url = http://localhost:8080/games/",
            "website_url = http://localhost:8080/",
            "db.default.driver = org.hsqldb.jdbcDriver",
            "db.default.username = sa",
            "db.default.password = none",
            "db.default.url = jdbc:hsqldb:mem:gardens",
        };
        Properties props = new Properties();
        try {
            props.load(new StringReader(Joiner.on("\n").join(defprops)));
        } catch (IOException ioe) {
            log.warning("Failed to parse default props", ioe); // won't happen
        }
        return new Config("toybox", props);
    }
}
