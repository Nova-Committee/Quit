package committee.nova.quit

import committee.nova.quit.proxies.CommonProxy
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{Mod, SidedProxy}
import org.apache.logging.log4j.LogManager

@Mod(modid = Quit.MODID, useMetadata = true, modLanguage = "scala")
object Quit {
  final val LOGGER = LogManager.getLogger
  final val MODID = "quit"
  final val packagePrefix = "committee.nova." + MODID + ".proxies."

  @SidedProxy(serverSide = packagePrefix + "CommonProxy", clientSide = packagePrefix + "ClientProxy")
  var proxy: CommonProxy = _

  @EventHandler def preInit(e: FMLPreInitializationEvent): Unit = proxy.preInit(e)

  @EventHandler def init(e: FMLInitializationEvent): Unit = proxy.init(e)

  @EventHandler def postInit(e: FMLPostInitializationEvent): Unit = proxy.postInit(e)
}
