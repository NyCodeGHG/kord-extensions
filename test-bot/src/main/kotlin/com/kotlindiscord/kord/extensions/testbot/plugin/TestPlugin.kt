/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.kotlindiscord.kord.extensions.testbot.plugin

import com.kotlindiscord.kord.extensions.plugins.KordExPlugin
import com.kotlindiscord.kord.extensions.plugins.annotations.plugins.WiredPlugin
import org.pf4j.PluginWrapper

@WiredPlugin(
    TestPlugin.PLUGIN_ID,
    "0.0.1",
    "kord-ex",
    "KordEx testing plugin",
    "MPL 2.0"
)
public class TestPlugin(wrapper: PluginWrapper) : KordExPlugin(wrapper) {
    override suspend fun setup() {
        extension(::TestPluginExtension)
    }

    public companion object {
        public const val PLUGIN_ID: String = "test-plugin"
    }
}