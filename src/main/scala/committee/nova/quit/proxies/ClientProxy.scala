package committee.nova.quit.proxies

import committee.nova.quit.client.key.KeyInit
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

class ClientProxy extends CommonProxy {
  override def preInit(event: FMLPreInitializationEvent): Unit = super.preInit(event)

  override def init(event: FMLInitializationEvent): Unit = {
    super.init(event)
    KeyInit.init()
  }

  override def postInit(event: FMLPostInitializationEvent): Unit = super.postInit(event)
}
