%====================================================================================
% robotradarsys description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/polar").
context(ctxradargui, "localhost",  "MQTT", "8038").
 qactor( radargui, ctxradargui, "it.unibo.radargui.Radargui").
  qactor( sonarsimulator, ctxradargui, "it.unibo.sonarsimulator.Sonarsimulator").
