%====================================================================================
% robotmind description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobotmind, "localhost",  "MQTT", "0").
context(ctxbasicrobot, "192.168.1.5",  "MQTT", "0").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( resourcemodel, ctxrobotmind, "it.unibo.resourcemodel.Resourcemodel").
  qactor( onestepahead, ctxrobotmind, "it.unibo.onestepahead.Onestepahead").
  qactor( robotmind, ctxrobotmind, "it.unibo.robotmind.Robotmind").
  qactor( walker, ctxrobotmind, "it.unibo.walker.Walker").
