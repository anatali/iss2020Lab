%====================================================================================
% smartrobot description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxsmartrobot, "localhost",  "MQTT", "0").
context(ctxbasicrobot, "192.168.1.5",  "MQTT", "0").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( smartrobot, ctxsmartrobot, "it.unibo.smartrobot.Smartrobot").
