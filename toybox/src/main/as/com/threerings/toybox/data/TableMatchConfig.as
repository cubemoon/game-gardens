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

package com.threerings.toybox.data {

import com.threerings.io.ObjectInputStream;
import com.threerings.io.ObjectOutputStream;

import com.threerings.parlor.game.data.GameConfig;

/**
 * Extends <code>MatchConfig</code> with information about match-making in the table style.
 */
public class TableMatchConfig extends MatchConfig
{
    /** The minimum number of seats at this table. */
    public var minSeats :int;

    /** The starting setting for the number of seats at this table. */
    public var startSeats :int;

    /** The maximum number of seats at this table. */
    public var maxSeats :int;

    /** This is set to true if this is a party game. */
    public var isPartyGame :Boolean;

    public function TableMatchConfig ()
    {
    }

    // from MatchConfig
    override public function getMatchType () :int
    {
        return isPartyGame ? GameConfig.PARTY : GameConfig.SEATED_GAME;
    }

    // from MatchConfig
    override public function getMinimumPlayers () :int
    {
        return minSeats;
    }

    // from MatchConfig
    override public function getMaximumPlayers () :int
    {
        return maxSeats;
    }

    // from interface Streamable
    override public function readObject (ins :ObjectInputStream) :void
    {
        super.readObject(ins);
        minSeats = ins.readInt();
        startSeats = ins.readInt();
        maxSeats = ins.readInt();
        isPartyGame = ins.readBoolean();
    }

    // from interface Streamable
    override public function writeObject (out :ObjectOutputStream) :void
    {
        super.writeObject(out);
        out.writeInt(minSeats);
        out.writeInt(startSeats);
        out.writeInt(maxSeats);
        out.writeBoolean(isPartyGame);
    }
}
}
