//
// ToyBox library - framework for matchmaking networked games
// Copyright (C) 2005-2011 Three Rings Design, Inc., All Rights Reserved
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

package com.threerings.toybox.data;

import com.threerings.util.ActionScript;

import com.threerings.parlor.data.Parameter;

/**
 * Models a parameter that is used to configure AIs.
 */
@ActionScript(omit=true)
public class AIParameter extends Parameter
{
    /** Indicates the maximum number of AIs in the game. */
    public int maximum;

    // TODO: allow specification of difficulty range
    // TODO: allow specification of personality types

    @Override // documentation inherited
    public String getLabel ()
    {
        return "m.ai_" + ident;
    }

    @Override // documentation inherited
    public Object getDefaultValue ()
    {
        return 0;
    }
}
