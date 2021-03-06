//
// Game Gardens - a platform for hosting simple multiplayer Java games
// Copyright (c) 2005-2013, Three Rings Design, Inc. - All rights reserved.
// https://github.com/threerings/game-gardens/blob/master/LICENSE

package com.threerings.gardens.web.logic

import com.google.inject.Inject

import com.samskivert.servlet.RedirectException
import com.samskivert.servlet.user.User
import com.samskivert.servlet.util.{FriendlyException, ParameterUtil}
import com.samskivert.velocity.InvocationContext

import com.threerings.gardens.server.GardensConfig
import com.threerings.gardens.web.GardensApp

/**
 * Handles logging a user in as 'tester' when running in test mode.
 */
class test_auth @Inject() (config :GardensConfig) extends AuthLogic {

  override def invoke (ctx :InvocationContext, app :GardensApp, user :User) {
    if (!config.testMode) throw new RedirectException("/")
    val from = ParameterUtil.getParameter(ctx.getRequest, "from", "/")
    // if they're not already logged in, log them in
    if (user == null) app.userManager.getRepository.loadUser("tester") match {
      case null => _log.warning("No test user, cannot test auth.")
      case tuser => effectLogin(ctx, app, tuser)
    }
    // then send them back to the from page
    throw new RedirectException(from)
  }
}
