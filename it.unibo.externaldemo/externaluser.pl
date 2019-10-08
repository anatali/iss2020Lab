%====================================================================================
% externaluser description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxexternaluser, "localhost",  "MQTT", "0" ).
context(ctxexternalresource, "192.168.1.12",  "MQTT", "0" ).
 qactor( resource, ctxexternalresource, "external").
  qactor( user, ctxexternaluser, "it.unibo.user.User").
