%====================================================================================
% radarsys description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxradargui, "localhost",  "MQTT", "0").
 qactor( radar, ctxradargui, "it.unibo.radar.Radar").
  qactor( demo, ctxradargui, "it.unibo.demo.Demo").
