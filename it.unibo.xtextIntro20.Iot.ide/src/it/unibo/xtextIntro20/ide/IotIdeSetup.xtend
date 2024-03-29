/*
 * generated by Xtext 2.18.0.M3
 */
package it.unibo.xtextIntro20.ide

import com.google.inject.Guice
import it.unibo.xtextIntro20.IotRuntimeModule
import it.unibo.xtextIntro20.IotStandaloneSetup
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class IotIdeSetup extends IotStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new IotRuntimeModule, new IotIdeModule))
	}
	
}
