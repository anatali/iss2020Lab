%====================================================================================
% basicrobotusage description   
%====================================================================================
context(ctxrobotusage, "localhost",  "TCP", "8028").
context(ctxbasicrobot, "192.168.1.12",  "TCP", "8018").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robotusage, ctxrobotusage, "it.unibo.robotusage.Robotusage").