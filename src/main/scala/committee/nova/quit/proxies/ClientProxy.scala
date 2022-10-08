package committee.nova.quit.proxies

import committee.nova.quit.Quit
import committee.nova.quit.client.key.KeyInit
import committee.nova.quit.compat.ModernKeyBindingCompat
import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

class ClientProxy extends CommonProxy {
  override def preInit(event: FMLPreInitializationEvent): Unit = super.preInit(event)

  override def init(event: FMLInitializationEvent): Unit = {
    super.init(event)
    KeyInit.init()
  }

  override def postInit(event: FMLPostInitializationEvent): Unit = {
    super.postInit(event)
    if (Loader.isModLoaded("mkb")) ModernKeyBindingCompat.init()
    else Array("Modern KeyBinding not found...", "Why not download it and have a try?").foreach(m => Quit.LOGGER.info(m))
  }
}
