//
// $Id$

package com.threerings.toybox.lobby.data;

import java.util.Iterator;

import com.samskivert.util.ArrayIntSet;

import com.threerings.presents.dobj.DSet;
import com.threerings.crowd.data.PlaceObject;

import com.threerings.parlor.data.Table;
import com.threerings.parlor.data.TableLobbyObject;

/**
 * Presently the lobby object contains nothing specific, but the class
 * acts as a placeholder in case lobby-wide fields are needed in the
 * future.
 */
public class LobbyObject extends PlaceObject
    implements TableLobbyObject
{
    // AUTO-GENERATED: FIELDS START
    /** The field name of the <code>name</code> field. */
    public static final String NAME = "name";

    /** The field name of the <code>tableSet</code> field. */
    public static final String TABLE_SET = "tableSet";
    // AUTO-GENERATED: FIELDS END

    /** The name of the game we're match-making in this lobby. */
    public String name;

    /** A set containing all of the tables being managed by this lobby.
     * This may be empty if we're not using tables to match-make. */
    public DSet tableSet = new DSet();

    // documentation inherited
    public DSet getTables ()
    {
        return tableSet;
    }

    // documentation inherited from interface
    public void addToTables (Table table)
    {
        addToTableSet(table);
    }

    // documentation inherited from interface
    public void updateTables (Table table)
    {
        updateTableSet(table);
    }

    // documentation inherited from interface
    public void removeFromTables (Comparable key)
    {
        removeFromTableSet(key);
    }

    /**
     * Counts up the occupants of this lobby and of all games hosted from
     * this lobby (which we have to do somewhat inaccurately because the
     * actual games are hosted in an entirely different JVM so we just
     * assume that if a table says it has four players, that four people
     * are in the game).
     */
    public int countOccupants ()
    {
        // add the occupants of the room
        ArrayIntSet occids = new ArrayIntSet();
        for (int ii = 0; ii < occupants.size(); ii++) {
            occids.add(occupants.get(ii));
        }
        for (Iterator iter = tableSet.iterator(); iter.hasNext(); ) {
            Table table = (Table)iter.next();
            if (table.gameOid > 0) {
                for (int ii = 0; ii < table.bodyOids.length; ii++) {
                    occids.add(table.bodyOids[ii]);
                }
            }
        }
        // remove any zeros that got in from a zero bodyOid
        occids.remove(0);
        return occids.size();
    }

    // AUTO-GENERATED: METHODS START
    /**
     * Requests that the <code>name</code> field be set to the
     * specified value. The local value will be updated immediately and an
     * event will be propagated through the system to notify all listeners
     * that the attribute did change. Proxied copies of this object (on
     * clients) will apply the value change when they received the
     * attribute changed notification.
     */
    public void setName (String value)
    {
        String ovalue = this.name;
        requestAttributeChange(
            NAME, value, ovalue);
        this.name = value;
    }

    /**
     * Requests that the specified entry be added to the
     * <code>tableSet</code> set. The set will not change until the event is
     * actually propagated through the system.
     */
    public void addToTableSet (DSet.Entry elem)
    {
        requestEntryAdd(TABLE_SET, tableSet, elem);
    }

    /**
     * Requests that the entry matching the supplied key be removed from
     * the <code>tableSet</code> set. The set will not change until the
     * event is actually propagated through the system.
     */
    public void removeFromTableSet (Comparable key)
    {
        requestEntryRemove(TABLE_SET, tableSet, key);
    }

    /**
     * Requests that the specified entry be updated in the
     * <code>tableSet</code> set. The set will not change until the event is
     * actually propagated through the system.
     */
    public void updateTableSet (DSet.Entry elem)
    {
        requestEntryUpdate(TABLE_SET, tableSet, elem);
    }

    /**
     * Requests that the <code>tableSet</code> field be set to the
     * specified value. Generally one only adds, updates and removes
     * entries of a distributed set, but certain situations call for a
     * complete replacement of the set value. The local value will be
     * updated immediately and an event will be propagated through the
     * system to notify all listeners that the attribute did
     * change. Proxied copies of this object (on clients) will apply the
     * value change when they received the attribute changed notification.
     */
    public void setTableSet (DSet value)
    {
        requestAttributeChange(TABLE_SET, value, this.tableSet);
        this.tableSet = (value == null) ? null : (DSet)value.clone();
    }
    // AUTO-GENERATED: METHODS END
}
