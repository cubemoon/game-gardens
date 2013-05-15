//
// Game Gardens - a platform for hosting simple multiplayer Java games
// Copyright (c) 2005-2013, Three Rings Design, Inc. - All rights reserved.
// https://github.com/threerings/game-gardens/blob/master/LICENSE

package com.threerings.gardens.user;

import com.threerings.nexus.distrib.NexusException
import com.threerings.nexus.server.SessionLocal

/** Contains session data for an authenticated user. */
class User (val id :Int, val name :String)

/** [User] related utility methods. */
object User {

  /** Returns the currently authed user, or null if none. */
  def get :User = SessionLocal.get(classOf[User])

  /** Returns the currently authed user, or throws an exception if none. */
  def require :User = get match {
    case null => throw new NexusException("No currently authed user")
    case user => user
  }

  /** Registers a user's session information. */
  def register (userId :Int, username :String) :User = {
    val user = new User(userId, username)
    SessionLocal.set(classOf[User], user)
    // TODO: maybe mirror session disconnect into User.onDisconnect?
    // SessionLocal.getSession.onDisconnect().connect(...)
    user
  }
}
